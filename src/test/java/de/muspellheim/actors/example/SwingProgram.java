package de.muspellheim.actors.example;

import de.muspellheim.actors.example.actors.AlarmbellActor;
import de.muspellheim.actors.example.actors.SwingActorDialog;
import de.muspellheim.actors.example.actors.WatchdogActor;
import de.muspellheim.actors.example.actors.messages.CurrentTimeEvent;
import de.muspellheim.actors.example.portals.Clock;
import de.muspellheim.actors.example.portals.SwingAlarmclockDialog;
import de.muspellheim.actors.example.providers.Alarmbell;

public class SwingProgram {

    public static void main(String args[]) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        Alarmbell bell = new Alarmbell();
        Clock clock = new Clock();
        Watchdog dog = new Watchdog();
        SwingAlarmclockDialog dlg = new SwingAlarmclockDialog();

        AlarmbellActor bellActor = new AlarmbellActor(bell);
        WatchdogActor dogActor = new WatchdogActor(dog);
        SwingActorDialog dlgActor = new SwingActorDialog(dlg);

        dlgActor.messages.addHandler(e -> dogActor.receive(e));
        dogActor.messages.addHandler(e -> dlgActor.receive(e));
        dogActor.messages.addHandler(e -> bellActor.receive(e));

        clock.onCurrentTime.addHandler(t -> {
            CurrentTimeEvent e = new CurrentTimeEvent();
            e.currentTime = t;
            dlgActor.receive(e);
            dogActor.receive(e);
        });
    }

}
