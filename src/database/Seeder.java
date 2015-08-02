/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

import models.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Seeder class for testing.
 */
public class Seeder {

    /**
     * Seed the database with dummy data for testing
     */
    public void seed() {
        user();
        item();
        order();
        expense();
        wage();
    }

    /**
     * Create a new UserHandler instance and call its' create method.
     */
    private void user() {

        User user = new User();
        user.name = "John";
        user.surname = "Doe";
        user.email = "johndoe@gmail.com";
        user.password = "secret";
        user.userType = 1;
        user.create();
    }

    /**
     * Create a new Item instance and call its' create method.
     */
    private void item() {

        Item item = new Item();
        item.name = "pizza";
        item.sellPrice = 200.0;
        item.buyPrice = 100.0;
        item.stock = 70;
        item.create();
    }

    /**
     * Create a new Order instance and call its' create method.
     */
    private void order() {

        Order order = new Order();
        order.completed = false;
        order.canceled = false;
        order.itemId = 1;
        order.amount = 1;
        order.create();
    }

    /**
     * Create a new Order instance and call its' create method.
     */
    private void expense() {

        Expense expense = new Expense();
        expense.name = "NewExpense";
        expense.amount = 20.00;
        expense.create();
    }

    /**
     * Set a new wage and call its update method.
     */
    private void wage() {

        Wage wage = new Wage();
        wage.newWage = 200.0;
        wage.update();
    }
}
