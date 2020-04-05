package inge.progetto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Rappresenta la modalit&agrave; operativa di una determinato {@link Attuatore}e di conseguenza lo stato dell'{@link Artefatto}
 * ad esso associato. Permettendo cosi ad un attuatore, attraverso una specifica modalit&agrave;, di comandare
 * il comportamento dell'artefatto, misurato o comunque monitorato eventualmente da un {@link Sensore}.
 *
 * Una modalit&agrave; operativa pu&ograve; inoltre essere parametrica e quindi caratterizzata da dei {@link #parametri}: ad esempio un {@link Attuatore} destinato alla termoregolazione di un interno &egrave; eventulamente dotato di una modalit&agrave;
 * operativa parametrica, dove un parametro &egrave; la temperatura desiderata impostata dal fruitore.
 *
 * @author Parampal Singh, Mattia Nodari
 *
 * @see Informazione
 */
public class ModalitaOperativa extends Informazione
                                implements Serializable {


    /**
     * Insieme di parametri di una modalit&agrave; operativa.
     * Ciascun parametro &egrave; dotato di nome, diverso da quello degli altri parametri della medesima modalit&agrave; e assume un valore numerico
     */
    private HashMap<String, Integer> parametri;

    /**Costruttore della classe
     * La modalit&agrave; operativa &egrave; non parametrica completamente specificata dal manutentore con nome
     * @param valore nome/valore della modalit&agrave; operativa
     */
    public ModalitaOperativa(String valore) {
        super("modalitaOperativa");
        this.valore = valore;
        super.setTipo("NN");
        this.parametri = new HashMap<>();
    }

    /**Costruisce un istanza di modalit&agrave; operativa; in questo sar&agrave; una modalita operativa parametrica in quanto vengono definiti anche i
     * @param valore nome/valore della modalit&agrave; operativa
     * @param parametri parametri da specificare nel caso la modalit&agrave; sia parametrica
     */
    public ModalitaOperativa(String valore, HashMap<String, Integer> parametri) {
        super("modalitàOperativa");
        this.valore = valore;
        this.parametri = parametri;
        super.setTipo("NN");
    }

    /** Permette di modificare un parametro della modalità operativa
     * @param nome nome del parametro che si desidera modificare
     * @param valoreParam nuovo valore da assegnare al parametro
     */
    public void setParametro(String nome, int valoreParam) {
        if (parametri.isEmpty()) {
            System.out.println("!!! La modalità operativa non è parametrica !!! Riprova");
            return;
        }
        else if (!parametri.containsKey(nome)) {
            System.out.println("!!! La modalita operativa non ha un parametro con questo nome !!! Riprova");
            return;
        }


        parametri.put(nome,valoreParam);
        System.out.println("*** Il parametro è stato impostato correttamente al nuovo valore ***");
    }

    @Override
    public String getValore() {
        return (String) this.valore;
    }

    /**
     * Fornisce una rappresentazione testuale che descrive brevemente l'istanza
     * @return stampa il toString della modalità operativa
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Modalita Operativa: " + this.getValore());
        if (!this.parametri.isEmpty()) {
            out.append("\n### Parametri = ");
            for (String key : parametri.keySet()) {
                out.append("...[ ").append(key).append(":").append(parametri.get(key)).append(" ]");
            }
        }
        return out.toString();
    }

    /**
     * Permette di conoscere se la modalità operativa è parametrica o meno
     * @return True se la modalità operativa è paramentrica oppure false se non lo è
     */
    public boolean isParametrica() {
        return !parametri.isEmpty();
    }

    /**
     * Fornisce l'insieme dei parametri della modalità operativa
     * @return parametri della modalità operativa
     */
    public HashMap<String, Integer> getParametri() {
        return parametri;
    }

    /**Permette di specificare/modificare i parametri della modalità operativa
     * @param parametri nuovi parametri da assegnare alla modalità operativa
     */
    public void setParametri(HashMap<String, Integer> parametri) {
        this.parametri = parametri;
    }
}
