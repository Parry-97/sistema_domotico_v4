package inge.progetto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *Un artefatto &egrave; un oggetto(non naturale) il cui comportamento  pu&ograve; essere monitorato da un {@link Sensore}/i e comandato
 * da un {@link Attuatore}/i. Il sistema acquisce informazioni sull'artefatto tramite sensori ad esso associati e compie azioni tramite
 * degli attuatori. Un artefatto pu&ograve; trovarsi in una {@link Stanza}(es. una lampada) ma possono esistere anche artefatti 'liberi' ovvero
 * non collocati in una stanza(es. cancello).
 *
 * @see ModalitaOperativa
 * @author Parampal Singh, Mattia Nodari
 */
public class Artefatto {
    private String nome;
    /**
     * Rappresenta lo 'stato attuale' dell'artefatto, monitorato da sensori({@link #listaSensori}) e comandato
     * da attuatori({@link #listaAttuatori}) ad esso associati
     * @see ModalitaOperativa
     */
    private ModalitaOperativa statoAttuale;

    /**
     * lista dei sensori associati all'artefatto e che monitorano il suo {@link #statoAttuale}
     */
    private ArrayList<Sensore> listaSensori;

    /**
     * lista degli attuatori che comandano il comportamento({@link #statoAttuale}) dell'artefatto
     */
    private ArrayList<Attuatore> listaAttuatori;


    /**Costruttore per la specifica di un artefatto
     * @param nome nome dell'artefatto
     * @param statoAttuale stato/modalità di default per l'artefatto
     */
    public Artefatto(String nome, ModalitaOperativa statoAttuale) {
        this.statoAttuale = statoAttuale;
        this.nome = nome;
        this.listaSensori = new ArrayList<>();
        this.listaAttuatori = new ArrayList<>();
    }

    /**Fornisce lo stato attuale dell'artefatto
     * @return lo stato attuale/modalit&agrave; di funzionamento esibita nel presente
     */
    public ModalitaOperativa getStatoAttuale() {
        return statoAttuale;
    }

    /**Modifica lo stato attuale dell'artefatto e aggiorna i sensori ad esso associati
     * @param statoAttuale nuovo stato da assegnare all'artefatto
     */
    public void setStatoAttuale(ModalitaOperativa statoAttuale) {
        for (Sensore s: listaSensori) {
            s.modificaRilevazione(this.statoAttuale, statoAttuale);
        }
        this.statoAttuale = statoAttuale;
    }

    /**Fornisce il nome dell'artefatto
     * @return nome dell'artefatto
     */
    public String getNome() {
        return nome;
    }

    /**Permette di specificare un nome per l'artefatto
     * @param nome nuovo nome da assegnare all'artefatto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**Permette di ottenere la lista di sensori associati all'artefatto
     * @return la lista di sensori associati all'artefatto
     */
    public ArrayList<Sensore> getListaSensori() {
        return listaSensori;
    }

    /**Permette di specificare la lista di sensori collegati all'artefatto
     * @param listaSensori nuova lista di sensori da collegare all'artefatto
     */
    public void setListaSensori(ArrayList<Sensore> listaSensori) {
        this.listaSensori = listaSensori;
    }

    /**
     * Fornisce la lista di attuatori associati all'artefatto
     * @return la lista di attuatori associati all'artefatto
     */
    public ArrayList<Attuatore> getListaAttuatori() {
        return listaAttuatori;
    }

    /**Permette di specificare la lista/insieme di attuatori che comandano l'artefatto
     * @param listaAttuatori lista di attuatori da associare all'artefatto
     */
    public void setListaAttuatori(ArrayList<Attuatore> listaAttuatori) {
        this.listaAttuatori = listaAttuatori;
    }

    /**Permette di associare un sensore all'artefatto cossiche questi possa inoltre cominiciare a monitorare il suo comportamento,
     * aggiungendolo alla lista dei sensori gi&agrave; collegati
     * @param s nuovo sensore da associare all'artefatto
     */
    public void aggiungiSensore(Sensore s) {
        if(!listaSensori.isEmpty()) {
            for (Sensore sensore : listaSensori) {
                if (sensore.getNome().equals(s.getNome()) || sensore.getCategoria().getNome().equals(s.getCategoria().getNome())) {
                    System.out.println("!!! IMPOSSIBILE AGGINGERE SENSORE DELLA STESSA CATEGORIA !!!");
                    return;
                }
            }
        }
        s.setRilevazione(this.statoAttuale);
        listaSensori.add(s);
    }

    /**Associa un nuovo attuatore all'artefatto e aggiungere alla sua lista di attuatori
     * @param a nuovo attuatore da associare all'artefatto
     */
    public void aggiungiAttuatore(Attuatore a) {
        if(!listaAttuatori.isEmpty()) {
            for (Attuatore attuatore : listaAttuatori) {
                if (attuatore.getNome().equals(a.getNome()) || attuatore.getCategoria().getNome().equals(a.getCategoria().getNome())) {
                    System.out.println("IMPOSSIBILE AGGIUNGERE UN ATTUATORE DELLA STESSA CATEGORIA !!!");
                    return;
                }
            }
        }

        listaAttuatori.add(a);
        a.aggiungiArtefatto(this);
    }

    /**Fornisce una descrizione di tutti i dispositivi collegati all'artefatto
     * @return descrizione di sensori/attuatori collegati all'artefatto
     */
    public String visualizzaDispositivi() {
        StringBuilder visualizza = new StringBuilder("Nome Artefatto: " + this.getNome() + ", lista attuatori che lo comandano:\n");

        StringBuilder attStr = new StringBuilder();
        for (Attuatore a: listaAttuatori) {
            attStr.append(a.getNome()).append(", categoria: ").append(a.getCategoria().getNome()).append(", modalità attuale: ").append(a.getModalitaAttuale()).append("\n");
        }

        if (attStr.length() == 0)
            visualizza.append("!!! Nessun attuatore pilota presente !!!\n");
        else
            visualizza.append(attStr.toString());

        visualizza.append("E dispone dei seguenti sensori:\n");

        StringBuilder sensStr = new StringBuilder();

        for (Sensore s: listaSensori) {
            ArrayList<Informazione> infoRileva = s.getRilevazioni();
            sensStr.append("Nome: ").append(s.getNome()).append(", categoria: ").append(s.getCategoria().getNome()).append("\n");
            sensStr.append(infoRileva.get(0).toString()).append("\n");
            if(infoRileva.size() > 1) {
                sensStr.append("E dispone delle seguenti informazioni rilevabili: \n");
                for (int i = 1; i < s.getRilevazioni().size(); i++) {
                    sensStr.append(s.getRilevazioni().get(i).toString()).append("\n");
                }
            }
            sensStr.append("\n");
        }

        if (sensStr.length() == 0)
            visualizza.append("!!! Nessun sensore associato !!!\n");
        else
            visualizza.append(sensStr.toString());

        return visualizza.append("\n").toString();
    }
}
