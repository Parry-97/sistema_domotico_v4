package inge.progetto;

import java.io.*;
import java.util.*;

public class RuleParser {

    //TODO: Tradurre magari in italiano

    private String fileName;
    private Timer timer;

    public RuleParser() {
        this.fileName = "";
    }

    public void setUp(String fileName, ArrayList<Sensore> listaSensori, ArrayList<Attuatore> listaAttuatori) {
        this.fileName = fileName;
        this.timer = new Timer("TimerThread");
    }

    public void stopTimer() {
        this.timer.cancel();
    }

    public void writeRuleToFile(String text,boolean append) {
        if (fileName.isEmpty())
            return;

        try {
            FileWriter fileWriter = new FileWriter(fileName, append);
            PrintWriter writer = new PrintWriter(fileWriter);

            writer.println(text);

            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readRuleFromFile() {

        StringBuilder output = new StringBuilder();
        boolean presente = new File(this.fileName).exists();

        if (!presente) {
            return "";
        }
        try {
            FileReader reader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(reader);


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            bufferedReader.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public void applyRules(ArrayList<Sensore> listaSensori, ArrayList<Attuatore> listaAttuatori) {
        String readRules = this.readRuleFromFile();

        System.out.println("\n\n...APPLICAZIONE REGOLE...\n");

        if (readRules.equals("")) {
            System.out.println("XX Non sono state inserite regole per l'unita immobiliare XX\n");
            return;
        }

        String[] rules = readRules.split("\n");

        for (String r : rules) {
            if (!verificaAbilitazione(r,listaSensori,listaAttuatori) || r.startsWith("DISABLED -->"))
                continue;

            String r2 = r.replace("ENABLED --> IF ", "");
            String[] tokens = r2.split(" THEN ");

            boolean ris = calculate(tokens[0], listaSensori);
            System.out.println(r2 + " :: " + ris);
            if (ris)
                applyActions(tokens[1], listaAttuatori);
        }
    }

    //TODO: Fruitore puo anche selettivamente disabilitare singole regole in modo specifico !!! CHE COIONI
    private boolean verificaAbilitazione(String regola, ArrayList<Sensore> listaSensori, ArrayList<Attuatore> listaAttuatori) {
        for (Sensore sens: listaSensori) {
            if (!sens.isAttivo() && regola.contains(sens.getNome())) {
                return false;
            }
        }

        for (Attuatore att : listaAttuatori) {
            if (!att.isAttivo() && regola.contains(att.getNome())) {
                return false;
            }
        }
        return true;
    }

    //TODO: Implementare nelle regole
    public void cambiaAbilitazioneRegola(String target, boolean abil) {
        String[] letto = readRuleFromFile().split("\n");

        for (int i = 0; i < letto.length; i++) {
            if (letto[i].equals(target)) {
                if (abil) {
                    if (letto[i].startsWith("DISABLED --> "))
                        letto[i] = letto[i].replace("DISABLED --> ", "ENABLED --> ");

                } else {

                    if (letto[i].startsWith("ENABLED --> "))
                        letto[i] = letto[i].replace("ENABLED --> ", "DISABLED --> ");

                }
                break;
            }
        }

        writeRuleToFile("",false);
        for (String regola : letto) {
            writeRuleToFile(regola,true);
        }
    }

    //TODO: Controlla abilita regole con quel dispositivo da richiamare quando si abilita un sensore/attuatore
    public void abilitaRegoleconDispositivo(String nomeDispositivo, ArrayList<Sensore> listaSensori, ArrayList<Attuatore> listaAttuatori) {
        String[] regole = readRuleFromFile().split("\n");
        for (int i = 0; i < regole.length; i++) {
            if (regole[i].contains(nomeDispositivo)) {
                verificaAbilitazione(regole[i], listaSensori, listaAttuatori);
            }
        }
    }

    //TODO: disabilita regole con quel dispositivo da richiamare quando si disabilita un sensore/attuatore
    public void disabilitaRegolaConDispositivo(String nomeDispositivo) {
        String[] regole = readRuleFromFile().split("\n");
        for (int i = 0; i < regole.length; i++) {
            if (regole[i].contains(nomeDispositivo)) {
                cambiaAbilitazioneRegola(regole[i],false);
            }
        }
    }

    //TODO: Finire defininizione di azione programmata e il suo scheduling
    private void applyActions(String token, ArrayList<Attuatore> listaAttuatori) {
        for (String tok : token.split(" ; "))
            if (tok.contains("start")) {
                this.timer.schedule(new AzioneProgrammata(listaAttuatori, tok.split(" , ")[0]),
                        getTime(tok.split(" , ")[1]
                                .split(" := ")[1]));
            } else {
                apply(tok, listaAttuatori);
            }
    }

    private Date getTime(String time) {
        String[] timetokens = time.split("\\.");
        int hour = Integer.parseInt(timetokens[0]);
        int minute = Integer.parseInt(timetokens[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    private void apply(String act, ArrayList<Attuatore> listaAttuatori) {
        String[] toks = act.split(" := ");

        Attuatore actD = null;
        for (Attuatore att : listaAttuatori) {
            if (att.getNome().equals(toks[0])) {
                actD = att;
            }
        }

        if (actD == null)
            return;

        String modPrecedente = actD.getNome() + ": " + actD.getModalitaAttuale() + " -> ";
        if (toks[1].contains("|")) {
            String[] againTokens = toks[1].split("\\|");

            if (!againTokens[2].matches("-?[0-9]+"))
                return;

            actD.setModalitaAttuale(againTokens[0], againTokens[1], Integer.parseInt(againTokens[2]));
        } else {
            actD.setModalitaAttuale(toks[1]);
        }
        System.out.println(modPrecedente + actD.getModalitaAttuale() + "\n");

    }

    private boolean calculate(String cos, ArrayList<Sensore> listaSensori) {
        if (cos.equals("true"))
            return true;

        if (cos.contains("OR")) {
            String[] expTok = cos.split(" OR ", 2);
            return calculate(expTok[0], listaSensori) || calculate(expTok[1], listaSensori);
        }

        if (cos.contains("AND")) {
            String[] expTok = cos.split(" AND ", 2);
            return calculate(expTok[0], listaSensori) && calculate(expTok[1], listaSensori);
        }
        //TODO: Riguarda exp reg perche non accurata
        if (cos.matches("time ([<>=]|<=|>=) [0-2][0-9].[0-5][0-9]")) {
            return evalTimeExp(cos.split(" "));
        }

        //TODO: Migliorare magari la regex e renderla piu specifica...troppo generica forse cosi
        if (cos.matches("[^<>=\t\n ]+ ([<>=]|<=|>=) [^<>=\t\n ]+")) {
            String[] expTok = cos.split(" ");
            return getValExp(expTok, listaSensori);

        }
        return false;

    }

    //TODO: Testa
    private boolean evalTimeExp(String[] expTok) {
        Date currentDate = Calendar.getInstance().getTime();
        Date confDate = getTime(expTok[2]);
        String operator = expTok[1];

        if (operator.startsWith("<")) {
            if (operator.endsWith("="))
                return currentDate.compareTo(confDate) <= 0;
            else
                return currentDate.compareTo(confDate) < 0;

        } else if (operator.startsWith(">")) {
            if (operator.endsWith("="))
                return currentDate.compareTo(confDate) >= 0;
            else
                return currentDate.compareTo(confDate) > 0;

        } else {
            return currentDate.compareTo(confDate) == 0;
        }

    }

    private boolean getValExp(String[] toks, ArrayList<Sensore> listaSensori) {
        String var1 = toks[0];
        String operator = toks[1];
        String var2 = toks[2];


        String[] sensVar = var1.split("\\.");
        Sensore sens1 = null;

        for (Sensore s : listaSensori) {
            if (s.getNome().equals(sensVar[0])) {
                sens1 = s;
                break;
            }
        }

        if (sens1 == null)
            return false;

        Informazione misura1 = sens1.getInformazione(sensVar[1]);

        if (misura1 == null) {
            return false;
        }

        if (var2.matches("-?[0-9]+")) {

            if (misura1.getTipo().equals("NN"))
                return false;

            int value = (int) misura1.getValore();
            int num = Integer.parseInt(var2);

            System.out.println();

            return evalOp(operator, value, num);
        }
        //TODO: Controllare magari con nomi diversi per vedere che non causi errori
        if (var2.matches("[A-Za-z]([a-zA-Z0-9])*_[A-Za-z]([a-zA-Z0-9])+\\.([a-zA-Z0-9])+(_[A-Za-z][a-zA-Z0-9]*)*")) {
            String[] sensVar2 = var2.split("\\.");
            Sensore sens2 = null;

            for (Sensore s : listaSensori) {
                if (s.getNome().equals(sensVar2[0])) {
                    sens2 = s;
                    break;
                }
            }

            if (sens2 == null)
                return false;

            Informazione misura2 = sens2.getInformazione(sensVar2[1]);

            if (misura2 == null)
                return false;

            if (!misura1.getTipo().equals(misura2.getTipo()))
                return false;

            if (misura1.getTipo().equals("NN")) {
                String sca1 = (String) misura1.getValore();
                String sca2 = (String) misura2.getValore();

                if (!operator.equals("="))
                    return false;
                else
                    return sca1.equals(sca2);

            } else {
                int value1 = (int) misura1.getValore();
                int value2 = (int) misura2.getValore();

                return evalOp(operator, value1, value2);
            }
        }

        if (var2.matches("[a-zA-Z]+") && operator.equals("=")) {
            if (misura1.getTipo().equals("NN")) {
                String sca1 = (String) misura1.getValore();
                return var2.equals(sca1);
            }
        }

        return false;
    }

    private boolean evalOp(String operator, int value1, int value2) {
        if (operator.startsWith("<")) {
            if (operator.endsWith("="))
                return value1 <= value2;
            else
                return value1 < value2;

        } else if (operator.startsWith(">")) {
            if (operator.endsWith("="))
                return value1 >= value2;
            else
                return value1 > value2;

        } else {
            return value1 == value2;

        }
    }

    //TODO: Far vedere la regola da cui deriva azione programmata
    //TODO: Schedularla una sola volta
    //TODO: Gestire OUTPUT
    private class AzioneProgrammata extends TimerTask {

        private ArrayList<Attuatore> attuatori;
        private String azione;

        public AzioneProgrammata(ArrayList<Attuatore> attuatori, String azione) {
            this.attuatori = attuatori;
            this.azione = azione;
        }

        @Override
        public void run() {
            System.out.println("\n...AZIONE PROGRAMMATA...");
            apply(this.azione, this.attuatori);
        }
    }
}
