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

public class ExpenseHandler {

    Expense expense;
    TextArea logs;
    ObjectOutputStream out;

    Debug debug = new Debug();

    /**
     * Determine what the Model wants to do.
     *
     * @param expense Expense instance
     * @param out     Server Socket out stream
     * @param logs    Server logs
     */
    public ExpenseHandler(Expense expense, ObjectOutputStream out, TextArea logs) {

        this.expense = expense;
        this.logs = logs;
        this.out = out;

        if (expense.action.equals("CREATE"))
            create();
        else if (expense.action.equals("GET"))
            get();
        else if (expense.action.equals("DELETE"))
            delete();
    }

    /**
     * Call the create method within Expense.
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
}