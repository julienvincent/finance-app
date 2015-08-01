package coms;

import helpers.Debug;
import models.*;

import java.util.ArrayList;

/**
 * A dispatcher that stores instances of
 * any component that called the adapter
 * of this triad.
 */
public class Dispatcher {

    private static ArrayList<Events> events = new ArrayList<>();

    /**
     * Register a class instance as a callback or listener.
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
     * Trigger all implementations of wageUpdated and pass the Expense instance.
     *
     * @param wage Wage instance that was returned by the server.
     */
    public static void wageUpdated(Wage wage) {
        if (events.size() > 0)
            for (Events event : events)
                event.wageUpdated(wage);
    }

    /**
     * Trigger all implementations of itemsUpdated and pass the Expense instance.
     *
     * @param item Wage instance that was returned by the server.
     */
    public static void itemsUpdated(Item item) {
        if (events.size() > 0)
            for (Events event : events)
                event.itemsUpdated(item);
    }

    /**
     * @param order Order instance that was returned by the server
     */
    public static void orderedItemsUpdated(Order order) {
        if (events.size() > 0)
            for (Events event : events)
                event.orderedItemsUpdated(order);
    }
}