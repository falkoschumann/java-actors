package de.muspellheim.actors;

import javax.swing.*;

public abstract class SwingActor extends Actor {

    @Override
    public void receive(Object message) {
        SwingUtilities.invokeLater(() -> {
            try {
                work(message);
            } catch (Exception e) {
                handleException(e);
            }
        });
    }

}
