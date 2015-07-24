/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import database.Query;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Expense extends Model {

    public Integer id;
    public Integer itemId;
    public String name;
    public Integer amount;

    /**
     * Set the classes Identifier type-hint
     */
    public Expense() {

        super("expense");
    }

    /**
     * Create a new expense.
     *
     * @return Boolean
     */
    public Boolean create() {

        new Query().query("INSERT INTO expenses (" +
                "item_id, name, amount) " +
                "VALUES (" +
                itemId + ", '" +
                name + "', " +
                amount + ")");

        return true;
    }
}
