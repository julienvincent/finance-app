/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.admin.subframes.AddItem;
import applications.admin.subframes.EditItem;
import applications.admin.subframes.ViewItem;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.List;
import applications.resources.components.ScrollPane;
import coms.EventsAdapter;
import helpers.Debug;
import models.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Stock extends JComponent {

    Label label;
    Button edit, view, add, remove, back;

    List list;
    ScrollPane scroll;

    Item result;

    ArrayList<String> items = new ArrayList<>();

    Debug debug = new Debug();
    public static Item Item = new Item();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Stock() {

        setLayout(new GridBagLayout());

        label = new Label("Stock");

        list = new List();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        scroll = new ScrollPane(list);

        edit = new Button("EDIT", 15);
        view = new Button("VIEW", 15);
        add = new Button("ADD", 15);
        remove = new Button("REMOVE", 15);
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

        add.addActionListener(new ActionListener() {

            /**
             * Launch the AddItem Frame
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AddItem addItem = new AddItem();
            }
        });

        remove.addActionListener(new ActionListener() {

            /**
             * If there is an item selected, write a
             * DELETE actioned instance to the stream
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    Item.action = "DELETE";
                    Item.name = list.getSelectedValue().toString();
                    Admin.Socket.out(Item);
                }
            }
        });

        view.addActionListener(new ActionListener() {

            /**
             * Launch the ViewItem Frame
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    for (Item item : result.items) {
                        if (item.name.equals(list.getSelectedValue().toString()))
                            new ViewItem(item);
                    }
                }
            }
        });

        edit.addActionListener(new ActionListener() {

            /**
             * launch the EditItem Frame
             *
             * @param e Action action
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    for (Item item : result.items) {
                        if (item.name.equals(list.getSelectedValue().toString()))
                            new EditItem(item);
                    }
                }
            }
        });

        new EventsAdapter() {

            /**
             * If the parent component is connected, fetch all the items
             * in the database.
             */
            @Override
            public void connected() {
                Item.action = "GET";
                Admin.Socket.out(Item);
            }

            /**
             * When an item in the database changes, update the list
             * component with the new items.
             *
             * @param item Item instance returned by the server.
             */
            @Override
            public void itemsUpdated(Item item) {
                result = item;
                items = new ArrayList<>();
                for (Item i : item.items)
                    items.add(i.name);
                list.setListData(items.toArray());
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

        constraint.anchor = GridBagConstraints.CENTER;
        constraint.insets = new Insets(-50, 0, 10, 0);
        constraint.ipadx = 150;
        constraint.ipady = 150;
        constraint.gridy = 1;
        add(scroll, constraint);

        constraint.ipadx = 0;
        constraint.ipady = 0;
        constraint.gridy = 2;

        constraint.insets = new Insets(0, 0, 10, 300);
        add(add, constraint);
        constraint.insets = new Insets(0, 0, 10, 100);
        add(view, constraint);
        constraint.insets = new Insets(0, 100, 10, 0);
        add(remove, constraint);
        constraint.insets = new Insets(0, 300, 10, 0);
        add(edit, constraint);
    }
}
