/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.admin.subframes.AddExpense;
import applications.admin.subframes.EditExpense;
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
    Button add, edit, remove, back;

    ArrayList<String> expenses = new ArrayList<>();
    public static Expense Expense = new Expense();
    Debug debug = new Debug();
    EventsAdapter adapter;

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

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 350);
                Admin.home.setVisible(true);
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddExpense addExpense = new AddExpense();
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedValue() != null) {
                    EditExpense editExpense = new EditExpense(list.getSelectedValue().toString());
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedValue() != null) {
                    Expense.action = "DELETE";
                    Expense.name = list.getSelectedValue().toString();
                    Admin.Socket.out(Expense);
                }
            }
        });

        adapter = new EventsAdapter() {

            @Override
            public void connected() {
                Expense.action = "GET";
                Admin.Socket.out(Expense);
            }

            @Override
            public void expensesUpdated(Expense expense) {

                debug.debug(expense.name);
                expenses = new ArrayList<>();
                expenses.addAll(expense.expenses);
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

        constraint.insets = new Insets(0, 0, 10, 200);
        add(add, constraint);
        constraint.insets = new Insets(0, 0, 10, 0);
        add(edit, constraint);
        constraint.insets = new Insets(0, 200, 10, 0);
        add(remove, constraint);
    }
}
