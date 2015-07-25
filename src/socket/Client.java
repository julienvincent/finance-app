/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/
package socket;

import coms.Dispatcher;
import coms.EventsAdapter;
import helpers.Debug;
import models.*;

import java.io.*;
import java.net.*;

public class Client {

    ObjectInputStream in;
    volatile ObjectOutputStream out;
    Debug debug = new Debug();

    /**
     * Connect to a local socket server and start IO streams
     *
     * @param port Port to connect to
     */
    public void connect(Integer port) {

        try {
            Socket socket = new Socket("localhost", port);
            debug.debug("CONNECTED", "GREEN");

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            Dispatcher.connected();

            while (true) {
                try {
                    handleResponse(in.readObject());
                } catch (IOException e) {
                    debug.debug("CONNECTION_LOST", "ERROR");
                    break;
                }
            }
            connect(8080);
        } catch (Exception e) {
            debug.debug("Could not connect to host", "ERROR");
            try {
                if (out != null)
                    out.close();
                Thread.sleep(3000);
                connect(8080);
            } catch (InterruptedException ex) {
                debug.debug("Sleep failed", "ERROR");
            } catch (IOException ex) {
                debug.debug("Stream couldn't close", "ERROR");
            }
        }
    }

    /**
     * Write a specified model to the server.
     *
     * @param model Instance of model
     */
    public void out(Model model) {

        try {
            out.reset();
            out.writeObject(model);
            debug.debug("Sent serialized object to server. [" + model.model + "]");
        } catch (IOException e) {
            debug.debug("Could not write object", "ERROR");
            e.printStackTrace();
        }
    }

    /**
     * Handle the model response from the server.
     *
     * @param model Model instance returned by server.
     */
    private void handleResponse(Object model) {

        if (model instanceof User)
            ((User) model).dispatch();
        else if (model instanceof Order)
            ((Order) model).dispatch();
        else if (model instanceof Expense)
            ((Expense) model).dispatch();
        else if (model instanceof Wage)
            ((Wage) model).dispatch();
    }

    public void main(String[] args) throws InterruptedException {

        connect(8080);
    }
}
