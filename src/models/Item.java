/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import coms.Dispatcher;
import database.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Item model for mutations
 */
public class Item extends Model {

    ResultSet result;

    public Integer id;
    public String name;
    public Double sellPrice;
    public Double buyPrice;
    public Integer stock;

    public ArrayList<Item> items = new ArrayList<>();

    public Item() {

        super("item");
    }

    /**
     * Fire the itemsUpdated event and pass itself as a parameter.
     */
    @Override
    public void dispatch() {

        Dispatcher.itemsUpdated(this);
    }

    /**
     * Create a new Item.
     *
     * @return Boolean was successful.
     */
    public Boolean create() {

        sellPrice *= 100;
        buyPrice *= 100;

        new Query().query("INSERT INTO items (" +
                "name, sell_price, buy_price, stock) " +
                "VALUES ('" +
                name + "', " +
                sellPrice.intValue() + ", " +
                buyPrice.intValue() + ", " +
                stock + ")");

        dispatch();

        return true;
    }

    /**
     * Fetch all orders from the database and return them to the requesting client.
     *
     * @return Boolean was successful.
     */
    public Boolean getAll() {

        result = new Query().query("SELECT * FROM items ORDER BY id DESC");
        items = new ArrayList<>();

        try {
            while (result.next()) {
                Item item = new Item();
                item.id = result.getInt("id");
                item.name = result.getString("name");
                Integer sellTemp = result.getInt("sell_price");
                Integer buyTemp = result.getInt("buy_price");
                item.sellPrice = sellTemp.doubleValue() * 100;
                item.buyPrice = buyTemp.doubleValue() * 100;
                item.stock = result.getInt("stock");
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }

    /**
     * Delete a specified Item.
     *
     * @return Boolean getAll Run the fetch method of this instance.
     */
    public Boolean delete() {

        new Query().query("DELETE FROM items WHERE name = '" + name + "'");

        return getAll();
    }

    /**
     * Update a specified Item.
     *
     * @return Boolean getAll Run the fetch method of this instance.
     */
    public Boolean edit() {

        new Query().query("UPDATE items SET sell_price=" + sellPrice + ", " +
                "buy_price=" + buyPrice + ", " +
                "stock=" + stock + " WHERE name = '" + name + "'");

        return getAll();
    }
}
