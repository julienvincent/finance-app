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

/**
 * Handle Order actions.
 */
public class OrderHandler {

    Order order;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do and
     * fire the appropriate action.
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
            case "ITEMS":
                getOrderedItems();
                break;
            case "ORDER_COST":
                orderCost();
                break;
        }
    }

    /**
     * Call the create method within Orders.
     * If Order returns successfully, write the response to the stream.
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
     * If Order returns successfully, write the response to the stream.
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

    /**
     * Call the complete method within Orders.
     * If Order returns successfully, write the response to the stream.
     */
    private void complete() {

        try {
            if (order.complete()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the cancel method within Orders.
     * If Order returns successfully, write the response to the stream.
     */
    private void cancel() {

        try {
            if (order.cancel()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the getOrderedItems method within Orders.
     * If Order returns successfully, write the response to the stream.
     */
    private void getOrderedItems() {

        try {
            if (order.getOrderedItems()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the orderCost method within Orders.
     * If Order returns successfully, write the response to the stream.
     */
    private void orderCost() {

        try {
            if (order.orderCost()) {
                out.writeObject(order);
                debug.debug("Sent serialized object to client. [" + order.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}