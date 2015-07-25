/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket.subhandlers;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserHandler {

    User user;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model instance wants to do.
     *
     * @param user User instance
     * @param out  Socket Server out stream
     * @param logs Socket Server logs
     */
    public UserHandler(User user, ObjectOutputStream out, TextArea logs) {

        this.user = user;
        this.logs = logs;
        this.out = out;

        if (user.action.equals("AUTH"))
            auth();
    }

    /**
     * Call the auth method within User.
     */
    private void auth() {
        try {
            if (user.auth()) {
                debug.debug("server: AUTHORIZED", "BLUE", logs);
                out.writeObject(user);
            } else {
                debug.debug("server: UNAUTHORIZED", "ERROR", logs);
                out.writeObject(user);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
        debug.debug("Sent serialized object to client. [" + user.model + "]", logs);
    }
}
