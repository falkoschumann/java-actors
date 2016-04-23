/*
 * Copyright (c) 2016 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.actors;

import org.eclipse.swt.widgets.Display;

public abstract class SwtActor extends Actor {

    @Override
    public void receive(Object message) {
        Display.getDefault().asyncExec(() -> {
            try {
                work(message);
            } catch (Exception e) {
                handleException(e);
            }
        });
    }

}
