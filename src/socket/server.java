/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import applications.resources.components.TextArea;
import helpers.Debug;

import java.io.*;
import java.net.*;


public class Server {

    static Debug debug = new Debug();

    /**
     * Start a socket server
     */
    public void listen(Integer port, TextArea logs) {

        logs = logs != null ? logs : new TextArea();

        try {
            ServerSocket server = new ServerSocket(port);
            debug.debug("Server is running", "GREEN", logs);

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

    public void main(String[] args) throws InterruptedException {

        listen(8080, null);
    }
}
