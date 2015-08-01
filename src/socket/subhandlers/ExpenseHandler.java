/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package socket.subhandlers;

import applications.resources.components.TextArea;
import helpers.Debug;
import models.Expense;
import models.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Handle Expense actions.
 */
public class ExpenseHandler {

    Expense expense;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do and
     * fire the appropriate action.
     *
     * @param expense Expense instance
     * @param out     Server Socket out stream
     * @param logs    Server logs
     */
    public ExpenseHandler(Expense expense, ObjectOutputStream out, TextArea logs) {

        this.expense = expense;
        this.logs = logs;
        this.out = out;

        switch (expense.action) {
            case "CREATE":
                create();
                break;
            case "GET":
                get();
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
     * Call the create method within Expense.
     * If Expense returns successfully, write the response to the stream.
     */
    private void create() {
        try {
            if (expense.create()) {
                out.writeObject(expense);
                debug.debug("Sent serialized object to client. [" + expense.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the getAll method within Expense.
     * If Expense returns successfully, write the response to the stream.
     */
    private void get() {

        try {
            if (expense.getAll()) {
                out.writeObject(expense);
                debug.debug("Sent serialized object to client. [" + expense.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the remove method within Expense.
     * If Expense returns successfully, write the response to the stream.
     */
    private void delete() {

        try {
            if (expense.remove()) {
                out.writeObject(expense);
                debug.debug("Sent serialized object to client. [" + expense.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }

    /**
     * Call the edit method within Expense.
     * If Expense returns successfully, write the response to the stream.
     */
    private void edit() {

        try {
            if (expense.edit()) {
                out.writeObject(expense);
                debug.debug("Sent serialized object to client. [" + expense.model + "]", logs);
            }
        } catch (IOException e) {
            debug.debug("Couldn't write object", "ERROR", logs);
        }
    }
}