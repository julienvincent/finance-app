/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/
package socket;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.Model;
import models.User;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    Socket client;
    Debug debug = new Debug();
    ObjectInputStream in;
    volatile ObjectOutputStream out;
    TextArea logs;

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

    private void handleResponse(Object model) {

        debug.debug("Received serialized object from client. [" + ((Model) model).model + "]", logs);

        if (model instanceof User) {
            try {
                if (((User) model).auth()) {

                    debug.debug("server: AUTHORIZED", "BLUE", logs);
                    out.writeObject(model);
                } else {
                    debug.debug("server: UNAUTHORIZED", "ERROR", logs);
                    out.writeObject(model);
                }

                debug.debug("Sent serialized object to client. [" + ((User) model).model + "]", logs);
            } catch (IOException e) {
                debug.debug("Couldn't write object", "ERROR", logs);
            }
        }
    }
}