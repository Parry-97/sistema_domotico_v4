package inge.progetto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class MyTimer extends Timer {
    private ArrayList<String> azioniProgrammate;

    public MyTimer(String name) {
        super(name);
        this.azioniProgrammate = new ArrayList<>();
    }

    //TODO: Consultare con scemo bianco se azione è programmata prima di quella gia programmata????
    public void schedule(RuleParser.AzioneProgrammata task, Date time) {
        String azione = task.getAzione();

        if (!azioniProgrammate.contains(azione)) {
            azioniProgrammate.add(azione);
            super.schedule(task, time);
        }

    }

    public void eliminaTask(String azione) {
        this.azioniProgrammate.remove(azione);
    }
}
