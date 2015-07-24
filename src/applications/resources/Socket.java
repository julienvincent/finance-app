/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources;

import socket.Client;

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
