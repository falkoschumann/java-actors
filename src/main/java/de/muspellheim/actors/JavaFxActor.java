/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors;

import javafx.application.Platform;

public abstract class JavaFxActor extends Actor {

    @Override
    public void receive(Object message) {
        Platform.runLater(() -> {
            try {
                work(message);
            } catch (Exception e) {
                handleException(e);
            }
        });
    }

}
