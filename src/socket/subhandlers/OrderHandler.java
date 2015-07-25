/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket.subhandlers;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class OrderHandler {

    Order order;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do.
     *
     * @param order Order instance
     * @param out   Socket Server out stream
     * @param logs  Socket Server logs
     */
    public OrderHandler(Order order, ObjectOutputStream out, TextArea logs) {

        this.order = order;
        this.logs = logs;
        this.out = out;

        switch (order.action) {
            case "CREATE":
                create();
                break;
            case "GET":
                get();
                break;
            case "CANCEL":
                cancel();
                break;
            case "COMPLETE":
                complete();
                break;
        }
    }

    /**
     * Call the create method within Orders.
     */
    private void create() {
        try {
            if (order.create()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the getAll method within Orders.
     */
    private void get() {

        try {
            if (order.getAll()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    public void complete() {

        try {
            if (order.complete()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    public void cancel() {

        try {
            if (order.cancel()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}