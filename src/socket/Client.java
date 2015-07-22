/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket;

import helpers.Debug;

import java.io.*;
import java.net.*;

public class Client {

    /**
     * Connect to a local socket server on port 33036
     * @throws InterruptedException
     */
    private static void connect() throws InterruptedException {

        BufferedReader in;
        PrintWriter out;
        Debug debug = new Debug();

        try{
            Socket socket = new Socket("localhost", 8080);
            debug.debug("CONNECTED", "GREEN");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                try {
                    debug.debug(in.readLine(), "BLUE");
                    out.println("from client");
                } catch (IOException e) {
                    debug.debug("CONNECTION_LOST", "ERROR");
                    break;
                }
            }

            connect();
        } catch (Exception e) {
            debug.debug("Could not connect to host", "ERROR");
            Thread.sleep(3000);
            connect();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        connect();
    }
}
