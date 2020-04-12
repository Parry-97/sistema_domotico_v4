package inge.progetto;

import java.util.*;

public class MyTimer extends Timer {

    private HashMap<String,Date> azioniProgrammate;

    public MyTimer(String name) {
        super(name);
        this.azioniProgrammate = new HashMap<>();
    }

    //TODO: Testare con esecuzione codice
    public void schedule(RuleParser.AzioneProgrammata task, Date time) {

        if (time.compareTo(Calendar.getInstance().getTime()) < 0)
            return;

        String azione = task.getAzione();

        if (!azioniProgrammate.containsKey(azione)) {
            azioniProgrammate.put(azione,time);
            super.schedule(task, time);
        } else {
            if (azioniProgrammate.get(azione).compareTo(time) != 0)
                super.schedule(task,time);

        }

    }
    public void eliminaTask(String azione) {
        azioniProgrammate.remove(azione);
    }
}