/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket.subhandlers;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.Wage;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Handle all wage actions.
 */
public class WageHandler {

    Wage wage;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do and fire
     * the appropriate action.
     *
     * @param wage Wage instance
     * @param out  Socket Server out stream
     * @param logs Socket Server logs
     */
    public WageHandler(Wage wage, ObjectOutputStream out, TextArea logs) {

        this.wage = wage;
        this.logs = logs;
        this.out = out;

        if (wage.action.equals("UPDATE"))
            update();
        else if (wage.action.equals("GET"))
            get();
    }

    /**
     * Call the create method within Wage.
     * If Wage returns successfully, write the response to the stream.
     */
    private void update() {
        try {
            if (wage.update()) {
                out.writeObject(wage);
                debug.debug("Sent serialized object to client. [" + wage.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the get method within Wage.
     * If Wage returns successfully, write the response to the stream.
     */
    private void get() {
        new Debug().debug("here");
        try {
            if (wage.get()) {
                out.writeObject(wage);
                debug.debug("Sent serialized object to client. [" + wage.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}
