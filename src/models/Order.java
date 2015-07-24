/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import com.sun.deploy.Environment;
import database.Query;
import helpers.Debug;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;

public class Order extends Model {

    public String id;
    public Integer userId;
    public Boolean completed;
    public Boolean canceled;
    public Integer itemId;
    public Integer amount;

    /**
     * Set the classes Identifier type-hint
     */
    public Order() {

        super("order");
    }

    /**
     * Create a new order.
     * @return Boolean
     */
    public Boolean create() {

        id = new BigInteger(130, new SecureRandom()).toString(32).substring(0, 10);

        new Debug().debug(id);

        new Query().query("INSERT INTO orders (" +
                "id, user_id, completed, canceled) " +
                "VALUES ('" +
                id + "', " +
                userId + ", " +
                completed + ", " +
                canceled + ")");

        new Query().query("INSERT INTO ordered_items (" +
                "order_id, item_id, amount) " +
                "VALUES ('" +
                id + "', " +
                itemId + ", " +
                amount + ")");

        return true;
    }
}
