/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import applications.resources.components.TextArea;
import coms.EventsAdapter;
import helpers.Debug;

import java.io.*;
import java.net.*;


public class Server {

    static Debug debug = new Debug();

    /**
     * Start a Socket Server on the specified port and listen for
     * client connections. Opens a new thread with a handler for
     * each client that connects.
     *
     * @param port Port the server will start on.
     * @param logs TextArea to append logs.
     */
    public void listen(Integer port, TextArea logs) {

        logs = logs != null ? logs : new TextArea();

        try {
            ServerSocket server = new ServerSocket(port);
            debug.debug("Server is running\n", "GREEN", logs);

            while (true) {
                Handler handler;
                try {
                    handler = new Handler(server.accept(), logs);
                    Thread client = new Thread(handler);
                    client.start();
                } catch (IOException e) {
                    debug.debug("CLIENT_ACCEPT Failed", "ERROR", logs);
                    System.exit(-1);
                }
            }

        } catch (IOException e) {
            debug.debug("SERVER_START Failed", "ERROR", logs);
        }
    }

    /**
     * Used to run the server manually (testing purposes)
     *
     * @param args args
     */
    public void main(String[] args) {

        listen(8080, null);
    }
}
