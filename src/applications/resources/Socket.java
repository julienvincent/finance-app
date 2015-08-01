/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources;

import socket.Client;

/**
 * Shared socket connection to be
 * run on a new thread for each application
 */
public class Socket implements Runnable {

    static Client connection;

    public Socket(Client connection) {

        Socket.connection = connection;
    }

    @Override
    public void run() {

        connection.connect(8080);
    }
}
