package inge.progetto;

import java.util.ArrayList;

/**
 * Un unit&agrave; immobiliare &egrave; la struttura che 'raggruppa'/'contiene' le stanze, gli artefatti e ,in modo indiretto, anche i
 * sensori, attuatori ad essi associati. Le unit&agrave; immobiliari possono avere diverse destinazioni d'uso({@link #tipo}): residenziale,
 * commerciale, produttiva ecc. Un'unit&agrave; immbobiliare &egrave; articolata in un insieme di stanze e/o in un insieme di artefatti.
 * Globalmente l'unit&agrave; deve consistere in un almeno una {@link Stanza} o in almeno un {@link Artefatto}.
 *
 * @author Parampal Singh, Mattia Nodari
 */
public class UnitaImmobiliare {
    /**
     * insieme di stanze contenute nell'unit&agrave; immobiliare
     */
    private ArrayList<Stanza> listaStanze;

    /**
     * insieme di artefatti nell'unit&agrave; immobiliare e che non sono collocati entro le stanze
     * Ex: Un cancello non &egrave; collocato in una stanza, bens&igrave; all'esterno
     */
    private ArrayList<Artefatto> listaArtefatti;

    /**
     * insieme di attuatori contenuti nell'unit&agrave; immobiliare
     */
    private ArrayList<Attuatore> listaAttuatori;

    /**
     * insieme di sensori contenuti nell'unit&agrave; immobiliare
     */
    private ArrayList<Sensore> listaSensori;

    /**
     * tipo/destinazione d'uso dell'unit&agrave; immobiliare
     */
    private String tipo;

    private String nome;

    /**Costruttore per specifica di un oggetto UnitaImmboliare
     * @param tipo destinazione d'uso dell'unit&agrave; immobiliare
     * @param nome nome dell'unit&agrave; immobiliare
     */
    public UnitaImmobiliare(String tipo, String nome) {

        this.listaArtefatti = new ArrayList<>();
        this.listaStanze = new ArrayList<>();
        this.listaSensori = new ArrayList<>();
        this.listaAttuatori = new ArrayList<>();
        this.nome = nome;
        this.tipo = tipo;
    }

    /**
     * Permette di specificare un'istanza placeholder
     */
    public UnitaImmobiliare() {
        this.tipo = "";
        this.listaSensori = new ArrayList<>();
        this.listaAttuatori = new ArrayList<>();
    }

    /**Permette di ottenere lista/insieme di stanze nell'unit&agrave; immobiliare
     * @return lista di stanze presenti nell'unit&agrave; immobiliare
     */

    public ArrayList<Stanza> getListaStanze() {
        return listaStanze;
    }

    /**Permette di specificare lista/insieme di stanze in cui &egrave; articolata l'unit&agrave; immobiliare
     * @param listaStanze lista di stanze da assegnare all'unit&agrave; immobiliare
     */
    public void setListaStanze(ArrayList<Stanza> listaStanze) {
        this.listaStanze = listaStanze;
    }

    /**Permette di ottenere la lista degli artefatti presenti nell'unit&agrave; immobiliare e non collocati in stanze.
     * @return lista di artefatti nell'unit&agrave; immobiliare
     */
    public ArrayList<Artefatto> getListaArtefatti() {
        return listaArtefatti;
    }

    /**Permette di specificare la lista di artefatti 'liberi'(non collocati in stanze) presenti nell'unit&agrave; immobiliare
     * @param listaArtefatti lista di artefatti 'liberi' di cui &egrave; costituita l'unit&agrave; immobiliare
     */
    public void setListaArtefatti(ArrayList<Artefatto> listaArtefatti) {
        this.listaArtefatti = listaArtefatti;
    }

    /**Permette di ottenere il tipo/destinazione d'uso dell'unit&agrave; immmobiliare
     * @return destinazione di'uso dell'unit&agrave; immobiliare
     */
    public String getTipo() {
        return tipo;
    }

    /**Permette di specificare il tipo/destinazione d'uso dell'unit&agrave; immobiliare
     * @param tipo tipo/destinazione d'uso dell'unit&agrave; immobiliare
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Attuatore> getListaAttuatori() {
        return listaAttuatori;
    }
    /**Permette di specificare lista/insieme di attuatori di cui &egrave; costituita l'unit&agrave; immobiliare
     * @param listaAttuatori lista di attuatori da assegnare agli artefatti dell'untita' immobiliare
     */
    public void setListaAttuatori(ArrayList<Attuatore> listaAttuatori) {
        this.listaAttuatori = listaAttuatori;
    }

    /**Permette di ottenere lista/insieme di sensori nell'unit&agrave; immobiliare
     * @return lista di tutti i sensori presenti nell'unit&agrave; immobiliare
     */
    public ArrayList<Sensore> getListaSensori() {
        return listaSensori;
    }

    /**Permette di specificare lista/insieme di sensori di cui &egrave; costituita l'unit&agrave; immobiliare
     * @param listaSensori lista di sensori da assegnare agli artefatti o alle stanze dell'untita' immobiliare
     */
    public void setListaSensori(ArrayList<Sensore> listaSensori) {
        this.listaSensori = listaSensori;
    }

    /**Permette di specificare una nuova stanza da aggiungere all'unit&agrave; immobiliare
     * @param s nuova stanza da aggiungere all'unit&agrave; immobiliare
     */
    public void aggiungiStanza(Stanza s) {
        for (Stanza stanza : listaStanze) {
            if (stanza.getNome().equals(s.getNome())) {
                System.out.println("Una stanza " + s.getNome() + " è già presente");
                return;
            }
        }
        listaStanze.add(s);
        System.out.println("*** La stanza " + s.getNome() + " è stata correttamente aggiunta ***");
    }

    /**Permette di aggiungere artefatti, esterni a stanze, nell'unit&agrave; immobiliare
     * @param a nuovo artefatto da aggiungere
     */
    public void aggiungiArtefatto(Artefatto a) {
        for (Artefatto artefatto : listaArtefatti) {
            if (artefatto.getNome().equals(a.getNome())) {
                System.out.println("Artefatto già presente");
                return;
            }
        }
        listaArtefatti.add(a);
        System.out.println("*** Artefatto creato correttamente ***");

    }

    public void aggiungiAttuatore(Attuatore a) {
        for (Attuatore attuatore : listaAttuatori) {
            if (attuatore.getNome().equals(a.getNome())) {
                System.out.println("!!! Un attuatore " + a.getNome() + " è già presente !!!");
                return;
            }
        }
        listaAttuatori.add(a);
        System.out.println("*** L'attuatore " + a.getNome() + " è stato correttamente aggiunto ***");
    }

    /**Permette di specificare un nuovo sensore da aggiungere all'unit&agrave; immobiliare
     * @param s nuovo sensore da aggiungere ad un artefatto o ad una stanza
     */
    public void aggiungiSensore(Sensore s) {
        for (Sensore sensore : listaSensori) {
            if (sensore.getNome().equals(s.getNome())) {
                System.out.println("!!! Un sensore " + s.getNome() + " è già presente !!!");
                return;
            }
        }
        listaSensori.add(s);
        System.out.println("*** Il sensore " + s.getNome() + " è stato correttamente aggiunto ***");
    }

    /**Fornisce una rappresentazione testuale che descrive complessivamente l'unit&agrave; immobiliare, le stanze e gli artefatti in essa contenuti.
     * @return stringa descrittiva dell'intera unit&agrave; immobiliare
     */

    public String visualizzaDescrizione() {
        StringBuilder visualizza = new StringBuilder("\n§ Tipo unità immobiliare: " + this.getTipo() + ", è costituita dalle seguenti stanze:\n");

        StringBuilder visStanze = new StringBuilder();
        for (Stanza stanza : listaStanze) {
            visStanze.append(stanza.visualizzaDisposizione());
        }

        if (visStanze.length() > 0)
            visualizza.append(visStanze);
        else
            visualizza.append("XX Non sono state aggiunte all'unità immobiliare XX");

        visualizza.append("\n\n§ Artefatti esterni all'unità immobiliare:\n");

        StringBuilder visArte = new StringBuilder();

        for (Artefatto artefatto : listaArtefatti) {
            boolean trovato = false;
            for(Stanza stanza : listaStanze) {
                if(stanza.getListaArtefatti().contains(artefatto)) {
                    trovato = true;
                    break;
                }
            }
            if(!trovato)
                visArte.append(artefatto.visualizzaDispositivi());
        }

        if (visArte.length() > 0)
            visualizza.append(visArte);
        else
            visualizza.append("XX Non sono presenti artefatti esterni all'unità immobiliare XX");

        return visualizza.toString();
    }

    /**Fornisce il nome dell'unit&agrave; immobiliare
     * @return nome dell'unit&agrave; immobiliare
     */
    public String getNome() {
        return nome;
    }

    public void refreshLetture() {
        if(listaSensori.isEmpty())
            return;
        for (Sensore s : listaSensori)
            s.aggiornaInfo();

    }

}
