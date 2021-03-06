/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.menu.components;

import applications.menu.Menu;
import applications.resources.components.Button;
import applications.staff.Staff;
import coms.EventsAdapter;
import helpers.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Order;

public class Demo extends JComponent {

    Button order;

    Boolean connected = false;

    Order Order = new Order();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Demo() {

        setLayout(new GridBagLayout());

        order = new Button("PLACE ORDER!", null);

        order.addActionListener(new ActionListener() {

            /**
             * If connected, create a new order
             * and write it to the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected) {
                    Order.action = "CREATE";
                    Order.itemId = 1;
                    Order.amount = 5;
                    Menu.Socket.out(Order);
                }
            }
        });

        new EventsAdapter() {

            /**
             * Determine if the application connected to
             * the Socket Server
             */
            @Override
            public void connected() {
                connected = true;
            }
        };

        build();
    }

    /**
     * Layout of this Components sub-components
     */
    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 2;

        constraint.ipadx = 150;
        constraint.ipady= 50;
        add(order, constraint);

    }
}
