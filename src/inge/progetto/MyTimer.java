package inge.progetto;

import java.util.*;

/**
 * Sottoclasse custom di Timer utilizzata per azioni/assegnamenti programmati dettati da regola
 *
 * @author Parampal Singh, Mattia Nodari
 */
public class MyTimer extends Timer {

    /**
     * collezione di azioni programmate che devono ancora essere eseguite
     */
    private final HashMap<Date, String> azioniProgrammate;

    /**Costruttore di un istanza
     * @param name nome del thread del timer
     */
    public MyTimer(String name) {
        super(name);
        this.azioniProgrammate = new HashMap<>();
    }

    /**Schedula/programma l'azione da eseguire all'ora specificata
     * @param task azione da schedulare
     * @param time ora a cui l'azione deve essere eseguita
     */
    public void schedule(RuleParser.AzioneProgrammata task, Date time) {

        if (time.compareTo(Calendar.getInstance().getTime()) < 0)
            return;

        String azione = task.getAzione();

        if (!azioniProgrammate.containsValue(azione)) {
            azioniProgrammate.put(time,azione);
            super.schedule(task, time);

        } else {
            for (Map.Entry<Date, String> entry : azioniProgrammate.entrySet()) {
                if (entry.getValue().equals(azione) && entry.getKey().compareTo(time) != 0) {
                    super.schedule(task,time);
                    azioniProgrammate.put(time,azione);
                    break;
                }

            }
        }
    }

    /**Elimina azione specificata da quelle ancora in coda
     * @param azione azione da eliminare
     */
    public void eliminaTask(String azione) {
        for (Map.Entry<Date, String> entry : azioniProgrammate.entrySet()) {
            if (entry.getValue().equals(azione)) {
                azioniProgrammate.remove(entry.getKey());
                break;
            }

        }
    }
}
