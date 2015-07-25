package coms;

import helpers.Debug;
import models.Expense;
import models.Order;
import models.User;
import models.Wage;

import java.util.ArrayList;

public class Dispatcher {

    private static ArrayList<Events> events = new ArrayList<>();

    /**
     * Register a class instance as a callback.
     *
     * @param obj An instance of the listening Component.
     */
    public Dispatcher(Events obj) {
        events.add(obj);
    }

    /**
     * Trigger all implementations of connected.
     */
    public static void connected() {
        if (events.size() > 0)
            for (Events event : events) {
                event.connected();
            }
    }

    /**
     * Trigger all implementations of auth and pass the User instance
     *
     * @param user User instance that was returned by the server.
     */
    public static void auth(User user) {
        for (Events event : events)
            event.auth(user);
    }

    public static void ordersUpdated(Order order) {
        if (events.size() > 0)
            for (Events event : events)
                event.ordersUpdated(order);
    }

    /**
     * Trigger all implementations of expensesUpdated and pass the Expense instance.
     *
     * @param expense Expense instance that was returned by the server.
     */
    public static void expensesUpdated(Expense expense) {
        if (events.size() > 0)
            for (Events event : events)
                event.expensesUpdated(expense);
    }

    /**
     * Trigger all implementations of wagesUpdated and pass the Expense instance.
     *
     * @param wage Wage instance that was returned by the server.
     */
    public static void wageUpdated(Wage wage) {
        if (events.size() > 0)
            for (Events event : events)
                event.wageUpdated(wage);
    }
}