/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors;

import javafx.application.Platform;

/**
 * This actor process all messages in the Java FX UI thread.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
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
