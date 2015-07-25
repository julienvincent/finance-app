/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import coms.Dispatcher;
import database.Query;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Expense extends Model {

    ResultSet result;

    public Integer id;
    public String name;
    public Integer amount;

    public ArrayList<String> expenses = new ArrayList<>();

    /**
     * Set the classes Identifier type-hint
     */
    public Expense() {

        super("expense");
    }

    /**
     * Notify all listening Components that expenses have updated.
     */
    public void dispatch() {

        Dispatcher.expensesUpdated(this);
    }

    /**
     * Create a new expense.
     *
     * @return Boolean
     */
    public Boolean create() {

        new Query().query("INSERT INTO expenses (" +
                "name, amount) " +
                "VALUES ('" +
                name + "', " +
                amount + ")");

        dispatch();

        return true;
    }

    /**
     * Get all expenses and return them to the requesting client.
     * @return Boolean was successful
     */
    public Boolean getAll() {

        result = new Query().query("SELECT * FROM expenses ORDER BY id DESC");
        expenses = new ArrayList<>();

        try {
            while (result.next())
                expenses.add(result.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }

    /**
     * Delete the specified expense from the database.
     * @return Boolean Fetch the current expenses and return true.
     */
    public boolean remove() {

        new Query().query("DELETE FROM expenses WHERE name = '" + name + "'");

        return getAll();
    }
}
