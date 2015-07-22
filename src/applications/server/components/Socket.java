/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.server.components;

import applications.resources.components.TextArea;
import socket.Server;

public class Socket implements Runnable {

    TextArea logs;

    public Socket(TextArea logs) {

        this.logs = logs;
    }

    @Override
    public void run() {

        new Server().listen(8080, this.logs);
    }
}
