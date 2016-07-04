/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors.example.providers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JavaFxAlarmbell implements Alarmbell {

    public void ring() {
        try {
            Media media = new Media(getClass().getResource("Alarm.wav").toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
