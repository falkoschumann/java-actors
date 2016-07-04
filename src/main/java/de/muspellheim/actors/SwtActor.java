/*
 * Copyright (c) 2016 Falko Schumann
 * Released under the terms of the MIT License (MIT).
 */

package de.muspellheim.actors;

import org.eclipse.swt.widgets.Display;

/**
 * This actor process all messages in the SWT UI thread.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
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
