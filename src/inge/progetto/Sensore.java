package inge.progetto;

import java.util.ArrayList;

/**
 * Permette di modellizare un sensore gestibile da un sistema domotico. Il sensore &egrave; identificato da un {@link #nome},
 * {@link #categoria} e dalla misura che questo rileva({@link #rilevazioni}), la quale pu&ograve; essere lo 'stato' di un
 * {@link Artefatto} da questi monitorato o la misura di una grandezza fisica associata ad una {@link Stanza} (es. temperatura o pressione).
 * Si definiscono quindi, attraverso la sua {@link #categoria}, sensori di tipo 'fisici' e 'sensori di stato' che monitorano
 * rispettivamente una stanza o un artefatto.
 *
 * @author Parampal Singh, Mattia Nodari
 */
public class Sensore {
    /** nome del sensore*/
    private String nome;

    /** categoria a cui il sensore appartiene (guarda {@link CategoriaSensore})*/
    private CategoriaSensore categoria;

    /** informazione o misura che il sensore rileva (guarda {@link Informazione})*/
    private ArrayList<Informazione> rilevazioni;

    /** stato del sensore ovvero se questi &egrave; abilitato o meno, attivo o spento*/
    private boolean statoAttivazione;

    /**
     * Flag che informa se il sensore è già associato ad un artefatto o meno
     */
    private boolean connesso;

    /**
     * Costruttore della classe Sensore.
     * l'informazione inizialmente associata &egrave; di natura casuale(default)
     * @param nome nome da attribuire al sensore
     * @param categoria categoria di sensore a cui questo 'appartiene'
     * @see CategoriaSensore
     * @see Informazione
     */
    public Sensore(String nome, CategoriaSensore categoria) {
        this.nome = nome + "_" + categoria.getNome();
        this.categoria = categoria;
        this.rilevazioni = categoria.getInfoRilevabili();
        this.statoAttivazione = true;
        this.connesso = false;
    }

    /**Fornisce il nome del sensore
     * @return nome del sensore
     */
    public String getNome() {
        return nome;
    }

    /** Permette di modificare il nome del sensore
     * @param nome nuovo nome da attribuire al sensore
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Fornisce la categoria a cui 'appartiene' il sensore
     * @return categoria del sensore
     */
    public CategoriaSensore getCategoria() {
        return categoria;
    }

    /**Permette di ottenere l'informazione rilevata dal sensore
     * @return informazione
     */
    public ArrayList<Informazione> getRilevazioni() {
        return rilevazioni;
    }

    /** Permette di specificare esplicitamente l'informazione rilevabile dal sensore. Utilizzata principalmente per sensori
     *  che monitorano artefatti, in quanto devono 'essere associati' a quest'ultimi per tener traccia del loro comportamento.
     *  Mentre invece sensori di natura 'fisica' non sono associati a particolari artefatti e misurano autonomamente una grandezza
     *  come temperatura o pressione ed &egrave; quindi vietato alterarne il contenuto informativo. Il tipo di sensore e le informazioni
     *  da questo rilevabili sono definite nella sua categoria (vedi {@link CategoriaSensore})
     *
     * Mantenendo la logica precedente: si ha che se il sensore è fisico allora a questi non si possono associare artefatti
     * Quelli non fisici avranno come primo elemento Informazione di inforilevabili un informazione casuale inizialmente di nome STATO che viene viene cambiato
     * quando viene chiamato questo metodo.
     *
     * @param rilevazione nuova informazione che il sensore rileva
     * @see Informazione
     */
    public void setRilevazione(Informazione  rilevazione) {

        if(this.categoria.isFisico())
            System.out.println("Non è possibile alterare le rilevazioni del sensore! \n");
        else {
            this.rilevazioni.set(0, rilevazione);
            connesso = true;
        }

    }

    public void modificaRilevazione(Informazione info1, Informazione info2) {
        if (rilevazioni.contains(info1))
            rilevazioni.set(rilevazioni.indexOf(info1),info2);
    }

    public Informazione getInformazione(String nome) {
        for (Informazione info : this.rilevazioni) {
            if (info.getNome().equals(nome))
                return info;
        }

        return null;
    }

    public void aggiornaInfo(){
        for (Informazione info : this.rilevazioni) {
            if (info.getTipo().equals("M"))
                continue;
            info.aggiornaValore();
        }
    }

    /**
     *
     * @return una rappresentazione descrittiva in formato testuale di un sensore
     */
    @Override
    public String toString() {
        String visualizza ="Nome sensore: " + this.getNome() + ", rilevazioni effettuate:\n";
        for(Informazione info : this.getRilevazioni()) {
            if(!info.getNome().equals("Modalità Operativa"))
                visualizza += info.getNome() + info.getValore();
        }
        return  visualizza;
    }

    /**Permette di sapere se il sensore è gia associato ad un artefatto
     * @return True se il sensore è gia connesso ad un artefatto False altrimenti
     */
    public boolean isConnesso() {
        return connesso;
    }
}
