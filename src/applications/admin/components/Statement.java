/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.resources.components.Button;
import applications.resources.components.Label;
import coms.EventsAdapter;
import helpers.Debug;
import models.Expense;
import models.Order;
import models.User;
import models.Wage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statement extends JComponent {

    Label label, moneyOut, moneyIn, profit;
    Button back;

    Debug debug = new Debug();

    Expense Expenses = new Expense();
    Order Orders = new Order();
    User Users = new User();

    Double totalOut = 0.0;
    Double totalIn = 0.0;

    /**
     * Instantiate new components and add action listeners to them
     */
    public Statement() {

        setLayout(new GridBagLayout());

        label = new Label("Income Statement");
        moneyOut = new Label("Loading...");
        moneyIn = new Label("Loading...");
        profit = new Label("Loading...");


        back = new Button("BACK", 15);

        back.addActionListener(new ActionListener() {

            /**
             * Return to the Home component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 400);
                Admin.home.setVisible(true);
            }
        });

        new EventsAdapter() {

            /**
             * If the parent component is connected,
             * Get all record types from the database
             */
            @Override
            public void connected() {
                Expenses.action = "GET";
                Admin.Socket.out(Expenses);

                Users.action = "GET";
                Admin.Socket.out(Users);

                Orders.action = "ORDER_COST";
                Admin.Socket.out(Orders);
            }

            @Override
            public void expensesUpdated(Expense expense) {

                for (Expense e : expense.expenses) {

                    totalOut += e.amount.doubleValue() / 100;
                }
                moneyOut.setText("Money Out: R " + totalOut);
                profit.setText("Profit: R " + (totalIn - totalOut));
            }

            @Override
            public void usersUpdated(User user) {

                for (User u : user.users) {
                    if (u.userType == 0){
                        totalOut += Wages.wage;
                    }
                }
                moneyOut.setText("Money Out: R " + totalOut);
                profit.setText("Profit: R " + (totalIn - totalOut));
            }

            @Override
            public void orderCostUpdated(Order order) {

                totalIn += (order.orderCost.doubleValue() / 100);
                moneyIn.setText("Money In: R " + totalIn);
                profit.setText("Profit: R " + (totalIn - totalOut));
            }
        };

        build();
    }

    /**
     * Layout of this Components sub-components
     */
    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;

        constraint.insets = new Insets(0, 0, 50, 150);
        add(back, constraint);

        constraint.anchor = GridBagConstraints.CENTER;
        constraint.gridy = 1;
        constraint.insets = new Insets(0, 0, 10, 0);
        add(label, constraint);

        constraint.ipadx = 0;

        constraint.gridy = 2;
        add(moneyIn, constraint);

        constraint.gridy = 3;
        add(moneyOut, constraint);

        constraint.gridy = 4;
        add(profit, constraint);
    }
}