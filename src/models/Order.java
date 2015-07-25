/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import com.sun.deploy.Environment;
import coms.Dispatcher;
import database.Query;
import helpers.Debug;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order extends Model {

    ResultSet result;

    public String code;
    public Integer userId;
    public Boolean completed = false;
    public Boolean canceled = false;
    public Integer itemId;
    public Integer amount;

    public ArrayList<String> orders = new ArrayList<>();

    /**
     * Set the classes Identifier type-hint
     */
    public Order() {

        super("order");
    }

    /**
     * Notify all listening Components that there are new orders.
     */
    public void dispatch() {

        Dispatcher.ordersUpdated(this);
    }

    /**
     * Create a new order.
     *
     * @return Boolean was successful.
     */
    public Boolean create() {

        code = new BigInteger(130, new SecureRandom()).toString(32).substring(0, 10);

        new Query().query("INSERT INTO orders (" +
                "code, user_id, completed, canceled) " +
                "VALUES ('" +
                code + "', " +
                userId + ", " +
                completed + ", " +
                canceled + ")");

        new Query().query("INSERT INTO ordered_items (" +
                "order_code, item_id, amount) " +
                "VALUES ('" +
                code + "', " +
                itemId + ", " +
                amount + ")");

        dispatch();

        return true;
    }

    /**
     * Fetch all orders from the database and return them to the requesting client.
     * @return Boolean was successful.
     */
    public Boolean getAll() {

        result = new Query().query("SELECT * FROM orders ORDER BY id DESC");
        orders = new ArrayList<>();

        try {
            while (result.next())
                if (!result.getBoolean("canceled"))
                    if (!result.getBoolean("completed"))
                        orders.add(result.getString("code"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }

    /**
     * Cancel the specified Order.
     *
     * @return Boolean was successful.
     */
    public Boolean cancel() {

        new Query().query("UPDATE orders SET canceled=true WHERE code = '" + code + "'");

        return getAll();
    }

    /**
     * Mark the specified Order as completed.
     *
     * @return Boolean was successful.
     */
    public Boolean complete() {

        new Query().query("UPDATE orders SET completed=true WHERE code = '" + code + "'");

        return getAll();
    }
}
