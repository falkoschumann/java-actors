/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

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
