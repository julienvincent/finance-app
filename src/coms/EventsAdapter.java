/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package coms;

import helpers.Debug;
import models.*;

/**
 * Wrapper around registering to the dispatcher and
 * implementing the Events interface.
 * <p>
 * Note: *MUST* override all existing method within
 * the Events interface.
 */
public class EventsAdapter implements Events {

    public EventsAdapter() {

        new Dispatcher(this);
    }

    @Override
    public void connected() {
    }

    @Override
    public void auth(User user) {
    }

    @Override
    public void ordersUpdated(Order order) {
    }

    @Override
    public void expensesUpdated(Expense expense) {
    }

    @Override
    public void wageUpdated(Wage wage) {
    }

    @Override
    public void itemsUpdated(Item item) {
    }

    @Override
    public void orderedItemsUpdated(Order order) {
    }
}
