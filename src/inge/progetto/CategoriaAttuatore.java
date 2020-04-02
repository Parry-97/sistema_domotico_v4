package inge.progetto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ogni {@link Attuatore} &egrave; definito tramite una categoria a cui 'appartiene'. Una categoria di attuatori &egrave; univocamente identificata
 * da un {@link #nome}, da un {@link #testoLibero} di lunghezza massima predefinita e dall'insieme(eventualmente singoletto) delle modalit&agrave;
 * operative esibite da ogni attuatore di quella categoria
 * @see ModalitaOperativa
 *
 * @author Parampal Singh, Mattia Nodari
 *
 */
public class CategoriaAttuatore implements Serializable {
    /**
     * nome della categoria di attuatore
     */
    private String nome;

    /**
     * testo libero che descrive brevemente la categoria
     */
    private final String testoLibero;

    /**
     * insieme delle modalit&agrave; operative esibite da attuatori della categoria
     */
    private ArrayList<ModalitaOperativa> modalita;

    /**Costruttore per la specifica di un oggetto CategoriaAttuatore
     * @param nome nome della categoria di attuatore
     * @param testoLibero testo libero di descrizione della categoria
     */
    public CategoriaAttuatore(String nome, String testoLibero) {
        this.nome = nome;
        this.testoLibero = testoLibero;
        this.modalita = new ArrayList<>();
    }

    /**Fornisce il nome della categoria
     * @return nome della categoria
     */
    public String getNome() {
        return nome;
    }

    /**Permette di specificare un nuovo nome alla categoria
     * @param nome nuovo nome da assegnare alla categoria
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**Fornisce il testo libero per la categoria
     * @return testo libero della categoria
     */
    public String getTestoLibero() {
        return testoLibero;
    }

    /**Fornisce l'insieme delle modalit&agrave; operative esibite da attuatori appartenenti alla categoria
     * @return lista di {@link #modalita} operative
     */
    public ArrayList<ModalitaOperativa> getModalita() {
        return modalita;
    }

    /**Permette di aggiungere una nuova modalit&agrave; operativa a quelle gia presenti
     * @param mod nuova modalit&agrave; operativa da aggiungere
     */
    public void aggiungiModalitaOperativa(ModalitaOperativa mod) {
        if(!modalita.isEmpty()) {
            for (ModalitaOperativa m : modalita) {
                if(m.getValore().equals(mod.getValore())) {
                    System.out.println("!!! Modalità Operativa già presente per questa categoria !!!");
                    return;
                }
            }
        }
        modalita.add(mod);
        System.out.println("*** Modalità Operativa aggiunta correttamente ***");

    }

    /**Fornisce una descrizione in formato testuale della categoria
     * @return una stringa descrittiva per la categoria
     */
    public String visualizzaCategoriaAttuatore() {
        String visualizza = "Nome Categoria: " + this.getNome() + ", testo libero: " + this.getTestoLibero() + ", lista modalità operative:\n";

        for (ModalitaOperativa lista: modalita) {
            visualizza +=  lista.getValore() + "\n";
        }

        return visualizza;
    }
}
