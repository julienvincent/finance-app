/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import helpers.Debug;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    Socket client;
    Debug debug = new Debug();

    BufferedReader in = null;
    PrintWriter out = null;

    /**
     * Client instance running on new thread
     * @param client
     */
    Handler(Socket client) {

        this.client = client;
        debug.debug("CLIENT_CONNECTED", "GREEN");
    }

    /**
     * Listen to the client Stream and respond
     */
    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("text");
            while (true) {
                try {
                    debug.debug(in.readLine(), "BLUE");
                } catch (IOException e) {
                    debug.debug("CLIENT_DISCONNECTED", "ERROR");
                    break;
                }
            }
        } catch (IOException e) {
            debug.debug("CONNECTION_FAILED", "ERROR");
            System.exit(-1);
        }
    }
}
