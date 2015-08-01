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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statement extends JComponent {

    Label label;
    Button back;

    Debug debug = new Debug();

    Expense Expenses = new Expense();
    Order Orders = new Order();
    User Users = new User();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Statement() {

        setLayout(new GridBagLayout());

        label = new Label("Income Statement");

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

                Orders.action = "GET_COMPLETE";
                Admin.Socket.out(Orders);
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

        constraint.insets = new Insets(0, 0, 100, 300);
        add(back, constraint);
    }
}