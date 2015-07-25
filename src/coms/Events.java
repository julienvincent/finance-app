/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package coms;

import models.Expense;
import models.Order;
import models.User;
import models.Wage;

public interface Events {

    /**
     * Fired when a client establishes a connection
     * to the Socket Server.
     */
    public void connected();

    /**
     * Fired when a user is Authorized
     * @param user UserHandler instance returned by the server.
     */
    public void auth(User user);

    /**
     * Fired when orders are fetched from the database.
     * @param order Order instance returned by the server.
     */
    public void ordersUpdated(Order order);

    /**
     * Fired when expenses are fetched from the database.
     * @param expense Expense instance returned by the server.
     */
    public void expensesUpdated(Expense expense);

    /**
     * Fired when the wage is fetched from the database.
     * @param wage Wage instance returned by the server.
     */
    public void wageUpdated(Wage wage);
}