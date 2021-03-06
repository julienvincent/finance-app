/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.admin.subframes.AddExpense;
import applications.admin.subframes.EditExpense;
import applications.admin.subframes.ViewExpense;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.List;
import applications.resources.components.ScrollPane;
import coms.Dispatcher;
import coms.EventsAdapter;
import helpers.Debug;
import models.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public final class Expenses extends JComponent {

    Label label;
    List list;
    ScrollPane scroll;
    Button add, edit, remove, back, view;

    Expense result;

    ArrayList<String> expenses = new ArrayList<>();
    public static Expense Expense = new Expense();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Expenses() {

        setLayout(new GridBagLayout());

        label = new Label("Orders");

        list = new List();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        scroll = new ScrollPane(list);

        add = new Button("ADD", 14);
        edit = new Button("EDIT", 14);
        remove = new Button("REMOVE", 14);
        back = new Button("BACK", 14);
        view = new Button("VIEW", 14);

        back.addActionListener(new ActionListener() {

            /**
             * Return to the Home component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 350);
                Admin.home.setVisible(true);
            }
        });

        add.addActionListener(new ActionListener() {

            /**
             * Launch AddExpense Frame
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AddExpense addExpense = new AddExpense();
            }
        });

        edit.addActionListener(new ActionListener() {

            /**
             * Launch the EditExpense Frame
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedValue() != null) {
                    for (Expense expense : result.expenses) {
                        if (expense.name.equals(list.getSelectedValue().toString()))
                            new EditExpense(expense);
                    }
                }
            }
        });

        view.addActionListener(new ActionListener() {

            /**
             * Launch the ViewExpense Frame
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue() != null) {
                    for (Expense expense : result.expenses) {
                        if (expense.name.equals(list.getSelectedValue().toString()))
                            new ViewExpense(expense);
                    }
                }
            }
        });

        remove.addActionListener(new ActionListener() {

            /**
             * Set the Expense instance's action to DELETE and
             * write the instance to the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedValue() != null) {
                    Expense.action = "DELETE";
                    Expense.name = list.getSelectedValue().toString();
                    Admin.Socket.out(Expense);
                }
            }
        });

        new EventsAdapter() {

            /**
             * If the parent component is connected,
             * get all Expenses from the database
             */
            @Override
            public void connected() {
                Expense.action = "GET";
                Admin.Socket.out(Expense);
            }

            /**
             * When an expense changes, update the list component
             * with the new expenses.
             *
             * @param expense Expense instance returned by the server.
             */
            @Override
            public void expensesUpdated(Expense expense) {
                result = expense;
                expenses = new ArrayList<>();
                for (Expense e : expense.expenses)
                    expenses.add(e.name);
                list.setListData(expenses.toArray());
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
        add(edit, constraint);
        constraint.insets = new Insets(0, 100, 10, 0);
        add(view, constraint);
        constraint.insets = new Insets(0, 300, 10, 0);
        add(remove, constraint);
    }
}
