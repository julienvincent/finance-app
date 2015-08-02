/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import coms.Dispatcher;
import database.Query;
import helpers.Debug;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Order model for mutations
 */
public class Order extends Model {

    ResultSet result;

    public Integer id;
    public String code;
    public Boolean completed = false;
    public Boolean canceled = false;
    public Integer itemId;
    public Integer amount;

    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<String> items = new ArrayList<>();
    public Integer orderCost = 0;
    /**
     * Set the classes Identifier type-hint
     */
    public Order() {

        super("order");
    }

    /**
     * Notify all listening Components that there are new orders.
     */
    @Override
    public void dispatch(String action) {

        if (action != null)
            if (action.equals("ITEMS"))
                Dispatcher.orderedItemsUpdated(this);
            else if (action.equals("ORDER_COST"))
                Dispatcher.orderCostUpdated(this);
            else
                Dispatcher.ordersUpdated(this);
        else
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
                "code, completed, canceled) " +
                "VALUES ('" +
                code + "', " +
                completed + ", " +
                canceled + ")");

        new Query().query("INSERT INTO ordered_items (" +
                "order_code, item_id, amount) " +
                "VALUES ('" +
                code + "', " +
                itemId + ", " +
                amount + ")");

        dispatch(this.action);

        return true;
    }

    /**
     * Fetch all orders from the database and return them to the requesting client.
     *
     * @return Boolean was successful.
     */
    public Boolean getAll() {

        result = new Query().query("SELECT * FROM orders ORDER BY id DESC");
        orders = new ArrayList<>();

        try {
            while (result.next())
                if (!result.getBoolean("canceled"))
                    if (!result.getBoolean("completed")) {
                        Order order = new Order();
                        order.id = result.getInt("id");
                        order.code = result.getString("code");
                        orders.add(order);
                    }
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

    /**
     * Get all ordered items for a specified order.
     * - Dispatches
     *
     * @return Boolean was successful
     */
    public Boolean getOrderedItems() {

        result = new Query().query("SELECT * FROM ordered_items WHERE order_code = '" + code + "'");

        try {
            while (result.next()) {
                ResultSet itemSet = new Query().query("SELECT * FROM items WHERE id = " + result.getInt("item_id"));
                while (itemSet.next()) {
                    items.add("Item: " + itemSet.getString("name") + ", Amount: " + result.getInt("amount"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dispatch(this.action);

        result = null;

        return true;
    }

    public Boolean orderCost() {

        result = new Query().query("SELECT * FROM orders WHERE completed = true");

        try {
            while (result.next()) {
                ResultSet orderedItems = new Query().query("SELECT * FROM ordered_items WHERE order_code = '" + result.getString("code") + "'");
                while (orderedItems.next()) {
                    new Debug().debug("amount " + orderedItems.getInt("id"));
                    ResultSet items = new Query().query("SELECT * FROM items WHERE id = " + orderedItems.getInt("item_id"));
                    while (items.next()) {
                        new Debug().debug("price " + items.getInt("sell_price"));
                        orderCost += items.getInt("sell_price") * orderedItems.getInt("amount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }
}
