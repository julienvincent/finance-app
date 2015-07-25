/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import database.Query;

public class Item extends Model {

    public String name;
    public Integer sellPrice;
    public Integer buyPrice;
    public Integer stock;

    public Item() {

        super("item");
    }

    /**
     * Create a new Item.
     * @return Boolean was successful.
     */
    public Boolean create() {

        new Query().query("INSERT INTO items (" +
                "name, sell_price, buy_price, stock) " +
                "VALUES ('" +
                name + "', " +
                sellPrice + ", " +
                buyPrice + ", " +
                stock + ")");

        return true;
    }
}
