/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.staff.components;

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
    Button complete, cancel;

    ArrayList<String> orders = new ArrayList<>();
    Order Order = new Order();
    Debug debug = new Debug();
    EventsAdapter adapter;

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

        complete.addActionListener(new ActionListener() {
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
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    Order.action = "CANCEL";
                    Order.code = list.getSelectedValue().toString();
                    Staff.Socket.out(Order);
                }
            }
        });

        adapter = new EventsAdapter() {

            @Override
            public void connected() {
                Order.action = "GET";
                Staff.Socket.out(Order);
            }

            @Override
            public void ordersUpdated(Order order) {

                orders = new ArrayList<>();
                orders.addAll(order.orders);
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
        constraint.ipadx = 75;
        constraint.ipady = 15;
        constraint.gridy = 2;
        constraint.weightx = 2;
        constraint.insets = new Insets(0, 0, 10, 200);
        add(complete, constraint);

        constraint.insets = new Insets(0, 200, 10, 0);
        add(cancel, constraint);
    }
}
