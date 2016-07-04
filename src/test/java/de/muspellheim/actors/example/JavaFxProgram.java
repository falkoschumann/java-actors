/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors.example;

import de.muspellheim.actors.example.actors.AlarmbellActor;
import de.muspellheim.actors.example.actors.JavaFxDialogActor;
import de.muspellheim.actors.example.actors.WatchdogActor;
import de.muspellheim.actors.example.actors.messages.CurrentTimeEvent;
import de.muspellheim.actors.example.portals.Clock;
import de.muspellheim.actors.example.portals.JavaFxAlarmclockDialog;
import de.muspellheim.actors.example.providers.Alarmbell;
import de.muspellheim.actors.example.providers.JavaFxAlarmbell;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxProgram extends Application {

    private JavaFxAlarmclockDialog dialog;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        Alarmbell bell = new JavaFxAlarmbell();
        Clock clock = new Clock();
        Watchdog dog = new Watchdog();
        dialog = new JavaFxAlarmclockDialog();

        AlarmbellActor bellActor = new AlarmbellActor(bell);
        WatchdogActor dogActor = new WatchdogActor(dog);
        JavaFxDialogActor dlgActor = new JavaFxDialogActor(dialog);

        dlgActor.outbox.addHandler(e -> dogActor.receive(e));
        dogActor.outbox.addHandler(e -> dlgActor.receive(e));
        dogActor.outbox.addHandler(e -> bellActor.receive(e));

        clock.onCurrentTime.addHandler(t -> {
            CurrentTimeEvent e = new CurrentTimeEvent();
            e.currentTime = t;
            dlgActor.receive(e);
            dogActor.receive(e);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/muspellheim/actors/example/portals/JavaFxAlarmclockDialog.fxml"));
        loader.setController(dialog);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
