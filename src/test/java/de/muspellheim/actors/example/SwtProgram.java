/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors.example;

import de.muspellheim.actors.example.actors.AlarmbellActor;
import de.muspellheim.actors.example.actors.SwtActorDialog;
import de.muspellheim.actors.example.actors.WatchdogActor;
import de.muspellheim.actors.example.actors.messages.CurrentTimeEvent;
import de.muspellheim.actors.example.portals.Clock;
import de.muspellheim.actors.example.portals.SwtAlarmclockDialog;
import de.muspellheim.actors.example.providers.Alarmbell;
import de.muspellheim.actors.example.providers.DefaultAlarmbell;

public class SwtProgram {

    public static void main(String args[]) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        Alarmbell bell = new DefaultAlarmbell();
        Clock clock = new Clock();
        Watchdog dog = new Watchdog();
        SwtAlarmclockDialog dlg = new SwtAlarmclockDialog();

        AlarmbellActor bellActor = new AlarmbellActor(bell);
        WatchdogActor dogActor = new WatchdogActor(dog);
        SwtActorDialog dlgActor = new SwtActorDialog(dlg);

        dlgActor.outbox.addHandler(e -> dogActor.receive(e));
        dogActor.outbox.addHandler(e -> dlgActor.receive(e));
        dogActor.outbox.addHandler(e -> bellActor.receive(e));

        clock.onCurrentTime.addHandler(t -> {
            CurrentTimeEvent e = new CurrentTimeEvent();
            e.currentTime = t;
            dlgActor.receive(e);
            dogActor.receive(e);
        });

        dlg.open();
    }

}
