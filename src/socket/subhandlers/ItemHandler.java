/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket.subhandlers;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.Item;
import models.Wage;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Handle Item actions.
 */
public class ItemHandler {

    Item item;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do and
     * fire the appropriate action.
     *
     * @param item Item instance
     * @param out  Socket Server out stream
     * @param logs Socket Server logs
     */
    public ItemHandler(Item item, ObjectOutputStream out, TextArea logs) {

        this.item = item;
        this.logs = logs;
        this.out = out;

        switch (item.action) {
            case "GET":
                get();
                break;
            case "CREATE":
                create();
                break;
            case "DELETE":
                delete();
                break;
            case "EDIT":
                edit();
                break;
        }
    }

    /**
     * Call the get method within Item.
     * If Item returns successfully, write the response to the stream.
     */
    private void get() {

        try {
            if (item.getAll()) {
                out.writeObject(item);
                debug.debug("Sent serialized object to client. [" + item.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the create method within item.
     * If Item returns successfully, write the response to the stream.
     */
    private void create() {

        try {
            if (item.create()) {
                out.writeObject(item);
                debug.debug("Sent serialized object to client. [" + item.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the delete method within Item.
     * If Item returns successfully, write the response to the stream.
     */
    private void delete() {

        try {
            if (item.delete()) {
                out.writeObject(item);
                debug.debug("Sent serialized object to client. [" + item.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the edit method within Item.
     * If Item returns successfully, write the response to the stream.
     */
    private void edit() {

        try {
            if (item.edit()) {
                out.writeObject(item);
                debug.debug("Sent serialized object to client. [" + item.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}
