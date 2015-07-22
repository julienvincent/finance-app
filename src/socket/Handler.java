/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import applications.resources.components.TextArea;
import helpers.Debug;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    Socket client;
    Debug debug = new Debug();

    BufferedReader in = null;
    PrintWriter out = null;

    TextArea logs;

    /**
     * Client instance running on new thread
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
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("text");
            logs.append("\ntext");
            while (true) {
                try {
                    debug.debug(in.readLine(), "BLUE", logs);
                } catch (IOException e) {
                    debug.debug("CLIENT_DISCONNECTED", "ERROR", logs);
                    break;
                }
            }
        } catch (IOException e) {
            debug.debug("CONNECTION_FAILED", "ERROR", logs);
            System.exit(-1);
        }
    }
}
