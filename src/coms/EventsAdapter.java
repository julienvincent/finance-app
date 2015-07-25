/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package coms;

import helpers.Debug;
import models.Expense;
import models.Order;
import models.User;
import models.Wage;

/**
 * Wrapper around registering to the dispatcher and
 * implementing the Events interface.
 */
public class EventsAdapter implements Events {

    public EventsAdapter() {

        new Dispatcher(this);
    }

    @Override
    public void connected(){}

    @Override
    public void auth(User user) {}

    @Override
    public void ordersUpdated(Order order) {}

    @Override
    public void expensesUpdated(Expense expense){}

    @Override
    public void wageUpdated(Wage wage){}
}
