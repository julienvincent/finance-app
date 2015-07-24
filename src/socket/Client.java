/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/
package socket;

import helpers.Debug;
import models.Dispatcher;
import models.Model;
import models.User;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private void handleResponse(Object model) {

        if (model instanceof User)
            ((User)model).dispatch();
    }

    public void main(String[] args) throws InterruptedException {

        connect(8080);
    }
}
