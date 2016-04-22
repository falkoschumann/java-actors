package de.muspellheim.actors.example.providers;

import javax.sound.sampled.*;
import java.io.InputStream;

public class DefaultAlarmbell implements Alarmbell {

    public void ring() {
        try {
            InputStream in = getClass().getResourceAsStream("Alarm.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(in);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
