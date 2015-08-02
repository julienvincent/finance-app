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

/**
 * Handle User actions.
 */
public class UserHandler {

    User user;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model instance wants to do and
     * fire the appropriate action.
     *
     * @param user User instance
     * @param out  Socket Server out stream
     * @param logs Socket Server logs
     */
    public UserHandler(User user, ObjectOutputStream out, TextArea logs) {

        this.user = user;
        this.logs = logs;
        this.out = out;

        switch (user.action) {
            case "AUTH":
                auth();
                break;
            case "CREATE":
                create();
                break;
            case "GET":
                get();
                break;
        }
    }

    /**
     * Call the auth method within User.
     * If User returns successfully, write the response to the stream.
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

    /**
     * Call the create method within User.
     * If User returns successfully, write the response to the stream.
     */
    private void create() {
        try {
            if (user.create()) {
                debug.debug("server: CREATED USER", "BLUE", logs);
                out.writeObject(user);
            } else {
                debug.debug("server: FAILED TO CREATE USER", "ERROR", logs);
                out.writeObject(user);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
        debug.debug("Sent serialized object to client. [" + user.model + "]", logs);
    }

    private void get() {

        try {
            if (user.getAll()) {
                out.writeObject(user);
                debug.debug("Sent serialized object to client. [" + user.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}
