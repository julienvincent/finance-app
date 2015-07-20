/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import helpers.Debug;

import java.io.*;
import java.net.*;


public class Server {

    static Debug debug = new Debug();

    /**
     * Start a socket server
     */
    private static void listen() {

        try {
            ServerSocket server = new ServerSocket(33036);
            debug.debug("Server is running", "GREEN");

            while (true) {
                Handler handler;
                try {
                    handler = new Handler(server.accept());
                    Thread client = new Thread(handler);
                    client.start();
                } catch (IOException e) {
                    debug.debug("CLIENT_ACCEPT Failed", "ERROR");
                    System.exit(-1);
                }
            }

        } catch (IOException e) {
            debug.debug("SERVER_START Failed", "ERROR");
        }
    }

    private void handler() {

    }

    public static void main(String[] args) throws InterruptedException {

        listen();
    }
}
