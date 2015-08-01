/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.staff.components;

import applications.notify.Notify;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.List;
import applications.resources.components.ScrollPane;
import applications.staff.Staff;
import helpers.Debug;
import coms.EventsAdapter;
import models.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public final class OrderManager extends JComponent {

    Label label;
    List list;
    ScrollPane scroll;
    Button complete, cancel, view;

    Boolean connected = false;

    ArrayList<String> orders = new ArrayList<>();
    Order Order = new Order();
    Order result;
    Debug debug = new Debug();

    /**
     * Instantiate new components and add action listeners to them
     */
    public OrderManager() {

        setLayout(new GridBagLayout());

        label = new Label("Orders");

        list = new List();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        scroll = new ScrollPane(list);

        complete = new Button("COMPLETE", 14);
        cancel = new Button("CANCEL", 14);
        view = new Button("VIEW", 14);

        complete.addActionListener(new ActionListener() {

            /**
             * Determine if there is a selected order.
             *
             * - Set this Orders instance's action to COMPLETE
             * and write the instance to the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    Order.action = "COMPLETE";
                    Order.code = list.getSelectedValue().toString();
                    Staff.Socket.out(Order);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {

            /**
             * Determine if there is a selected order
             *
             * - Set this Orders instance's action to CANCEL
             * and write the instance to the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    Order.action = "CANCEL";
                    Order.code = list.getSelectedValue().toString();
                    Staff.Socket.out(Order);
                }
            }
        });

        view.addActionListener(new ActionListener() {

            /**
             * Determine if there is a selected order
             *
             * - Launch a new Frame and pass in an instance
             * of the selected order.
             *
             * @param e Action
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    for (Order order : result.orders) {
                        if (order.code == list.getSelectedValue().toString())
                            new ViewOrder(order);
                    }
                }
            }
        });

        new EventsAdapter() {

            @Override
            public void connected() {
                connected = true;
                Order.action = "GET";
                Staff.Socket.out(Order);
            }

            @Override
            public void ordersUpdated(Order order) {

                result = order;
                orders = new ArrayList<>();
                for (Order o : order.orders)
                    orders.add(o.code);
                list.setListData(orders.toArray());
            }
        };

        build();
    }

    /**
     * Layout of this Components sub-components
     */
    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.NORTHWEST;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(10, 30, 40, 0);

        add(label, constraint);

        constraint.anchor = GridBagConstraints.CENTER;
        constraint.gridy = 1;
        constraint.ipadx = 300;
        constraint.ipady = 130;
        constraint.insets = new Insets(-20, 0, 30, 0);
        add(scroll, constraint);

        constraint.anchor = GridBagConstraints.BASELINE;
        constraint.ipadx = 0;
        constraint.ipady = 0;
        constraint.gridy = 2;
        constraint.weightx = 2;
        constraint.insets = new Insets(0, 0, 10, 300);
        add(complete, constraint);

        constraint.insets = new Insets(0, 0, 10, 0);
        add(view, constraint);

        constraint.insets = new Insets(0, 300, 10, 0);
        add(cancel, constraint);
    }
}
