package de.muspellheim.actors.example;

import de.muspellheim.actors.example.actors.AlarmbellActor;
import de.muspellheim.actors.example.actors.SwtDlgActor;
import de.muspellheim.actors.example.actors.WatchdogActor;
import de.muspellheim.actors.example.actors.messages.CurrentTimeEvent;
import de.muspellheim.actors.example.portals.Clock;
import de.muspellheim.actors.example.portals.SwtDlgAlarmclock;
import de.muspellheim.actors.example.providers.Alarmbell;

public class SwtProgram {

    public static void main(String args[]) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        Alarmbell bell = new Alarmbell();
        Clock clock = new Clock();
        Watchdog dog = new Watchdog();
        SwtDlgAlarmclock dlg = new SwtDlgAlarmclock();

        AlarmbellActor bellActor = new AlarmbellActor(bell);
        WatchdogActor dogActor = new WatchdogActor(dog);
        SwtDlgActor dlgActor = new SwtDlgActor(dlg);

        dlgActor.event.addHandler(e -> dogActor.receive(e));
        dogActor.event.addHandler(e -> dlgActor.receive(e));
        dogActor.event.addHandler(e -> bellActor.receive(e));

        clock.onCurrentTime.addHandler(t -> {
            CurrentTimeEvent e = new CurrentTimeEvent();
            e.currentTime = t;
            dlgActor.receive(e);
            dogActor.receive(e);
        });
    }

}
