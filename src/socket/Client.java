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

/**
 * A client socket that is shared between all parent applications.
 */
public class Client {

    ObjectInputStream in;
    volatile ObjectOutputStream out;
    Debug debug = new Debug();

    /**
     * Connect to a local socket server and start IO streams
     * <p>
     * - On connection loss, attempt to reconnect every *3* seconds
     * <p>
     * - Only drastic write fails will shut down a connection permanently
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
     * Publicly available write point which allows
     * all child components to write to the server through
     * it's Parent.
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
     * Handle to model instance returned by the server.
     * - Determine to which sub instance it belongs to.
     * - Fire the appropriate dispatch call
     *
     * @param model Model instance returned from a client.
     */
    private void handleResponse(Object model) {

        if (model instanceof User)
            ((User) model).dispatch();
        else if (model instanceof Order)
            ((Order) model).dispatch(((Order) model).action);
        else if (model instanceof Expense)
            ((Expense) model).dispatch();
        else if (model instanceof Wage)
            ((Wage) model).dispatch();
        else if (model instanceof Item)
            ((Item) model).dispatch();
    }

    public void main(String[] args) throws InterruptedException {

        connect(8080);
    }
}
