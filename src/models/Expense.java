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

/**
 * Expense model for mutations
 */
public class Expense extends Model {

    ResultSet result;

    public Integer id;
    public String name;
    public Double amount;

    public ArrayList<Expense> expenses = new ArrayList<>();

    /**
     * Set the classes Identifier type-hint
     */
    public Expense() {

        super("expense");
    }

    /**
     * Notify all listening Components that expenses have updated.
     */
    @Override
    public void dispatch() {

        Dispatcher.expensesUpdated(this);
    }

    /**
     * Create a new expense.
     *
     * @return Boolean
     */
    public Boolean create() {

        amount *= 100;
        new Query().query("INSERT INTO expenses (" +
                "name, amount) " +
                "VALUES ('" +
                name + "', " +
                amount.intValue() + ")");

        dispatch();

        return true;
    }

    /**
     * Get all expenses and return them to the requesting client.
     *
     * @return Boolean was successful
     */
    public Boolean getAll() {

        result = new Query().query("SELECT * FROM expenses ORDER BY id DESC");
        expenses = new ArrayList<>();

        try {
            while (result.next()) {
                Expense expense = new Expense();
                expense.name = result.getString("name");
                Integer temp = result.getInt("amount");
                expense.amount = temp.doubleValue();
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }

    /**
     * Delete the specified expense from the database.
     *
     * @return Boolean Fetch the current expenses and return true.
     */
    public Boolean remove() {

        new Query().query("DELETE FROM expenses WHERE name = '" + name + "'");

        return getAll();
    }

    /**
     * Update a specific Expense.
     *
     * @return Boolean getAll Run the fetch method of this instance.
     */
    public Boolean edit() {

        amount *= 100;
        new Query().query("UPDATE expenses SET amount=" + amount.intValue() + " WHERE name = '" + name + "'");

        return getAll();
    }
}
