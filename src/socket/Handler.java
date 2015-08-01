/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/
package socket;

import applications.resources.components.TextArea;
import coms.Dispatcher;
import coms.EventsAdapter;
import helpers.Debug;
import models.*;
import socket.subhandlers.*;

import java.io.*;
import java.net.Socket;

/**
 * A handler that runs on a thread, handles connected clients
 */
public class Handler implements Runnable {

    Socket client;
    Debug debug = new Debug();
    ObjectInputStream in;
    volatile ObjectOutputStream out;
    TextArea logs;

    EventsAdapter adapter;

    /**
     * Update state, log that a client has connected.
     *
     * @param client Client Socket
     */
    Handler(Socket client, TextArea logs) {

        this.client = client;
        this.logs = logs;
        debug.debug("CLIENT_CONNECTED", "GREEN", logs);
    }

    /**
     * Listen to the client's in stream, open an out stream.
     * - Pass communications to the handleResponse() method.
     */
    @Override
    public void run() {

        try {
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());

            listen();

            while (true) {
                try {
                    try {
                        handleResponse(in.readObject());
                    } catch (ClassNotFoundException e) {
                        debug.debug("Unable to read object", "ERROR", logs);
                    }
                } catch (IOException e) {
                    debug.debug("CLIENT_DISCONNECTED", "ERROR", logs);
                    out.close();
                    break;
                }
            }
        } catch (IOException e) {
            debug.debug("CONNECTION_FAILED", "ERROR", logs);
        }
    }

    /**
     * Handle to model instance returned by the client.
     * - Determine to which sub instance it belongs to.
     * - Fire an appropriate handler according to it's type.
     *
     * @param model Model instance returned from a client.
     */
    private void handleResponse(Object model) {

        debug.debug("Received serialized object from client. [" + ((Model) model).model + "]", logs);

        if (model instanceof User)
            new UserHandler((User) model, out, logs);
        else if (model instanceof Order)
            new OrderHandler((Order) model, out, logs);
        else if (model instanceof Expense)
            new ExpenseHandler((Expense) model, out, logs);
        else if (model instanceof Wage)
            new WageHandler((Wage) model, out, logs);
        else if (model instanceof Item)
            new ItemHandler((Item) model, out, logs);
    }

    /**
     * Fires off collection listeners in order to notify
     * connected clients that data has changed.
     */
    private void listen() {

        adapter = new EventsAdapter() {

            /**
             * Collect orders
             * @param order Order instance
             */
            @Override
            public void ordersUpdated(Order order) {

                order.action = "GET";
                new OrderHandler(order, out, logs);
            }

            /**
             * Collect expenses.
             * @param expense Expense instance
             */
            @Override
            public void expensesUpdated(Expense expense) {

                expense.action = "GET";
                new ExpenseHandler(expense, out, logs);
            }

            /**
             * Collect wages.
             * @param wage Wage instance
             */
            @Override
            public void wageUpdated(Wage wage) {

                wage.action = "GET";
                new WageHandler(wage, out, logs);
            }

            /**
             * Collect items.
             * @param item Item instance
             */
            @Override
            public void itemsUpdated(Item item) {

                item.action = "GET";
                new ItemHandler(item, out, logs);
            }
        };
    }
}