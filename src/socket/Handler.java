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
import socket.subhandlers.ExpenseHandler;
import socket.subhandlers.OrderHandler;
import socket.subhandlers.UserHandler;
import socket.subhandlers.WageHandler;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    Socket client;
    Debug debug = new Debug();
    ObjectInputStream in;
    volatile ObjectOutputStream out;
    TextArea logs;

    EventsAdapter adapter;

    /**
     * Client instance running on new thread
     *
     * @param client Client Socket
     */
    Handler(Socket client, TextArea logs) {

        this.client = client;
        this.logs = logs;
        debug.debug("CLIENT_CONNECTED", "GREEN", logs);
    }

    /**
     * Listen to the client Stream and respond
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
    }

    /**
     * Listen for store updates and notify the associated clients.
     */
    private void listen() {

        adapter = new EventsAdapter() {

            @Override
            public void ordersUpdated(Order order) {

                order.action = "GET";
                new OrderHandler(order, out, logs);
            }

            @Override
            public void expensesUpdated(Expense expense) {

                expense.action = "GET";
                new ExpenseHandler(expense, out, logs);
            }

            @Override
            public void wageUpdated(Wage wage) {

                wage.action = "GET";
                new WageHandler(wage, out, logs);
            }
        };
    }
}