/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors;

import javax.swing.*;

/**
 * This actor process all messages in the Swing UI thread.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
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
