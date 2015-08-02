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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Home extends JComponent {

    Label label;
    Button stock, wages, expenses, income, create;

    Debug debug = new Debug();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Home() {

        setLayout(new GridBagLayout());

        label = new Label("Home");

        stock = new Button("STOCK", 15);
        wages = new Button("WAGES", 15);
        expenses = new Button("EXPENSES", 15);
        income = new Button("INCOME STATEMENT", 15);
        create = new Button("REGISTER USER", 15);

        expenses.addActionListener(new ActionListener() {

            /**
             * Launch the Expenses component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 400);
                Admin.expenses.setVisible(true);
            }
        });

        wages.addActionListener(new ActionListener() {

            /**
             * Launch the Wages component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 400);
                Admin.wages.setVisible(true);
            }
        });

        create.addActionListener(new ActionListener() {

            /**
             * Launch the Register component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 500);
                Admin.register.setVisible(true);
            }
        });

        stock.addActionListener(new ActionListener() {

            /**
             * Launch the Stock component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 400);
                Admin.stock.setVisible(true);
            }
        });

        income.addActionListener(new ActionListener() {

            /**
             * Launch the Statement component
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(300, 250);
                Admin.statement.setVisible(true);
            }
        });

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
        constraint.gridwidth = 1;

        constraint.insets = new Insets(-20, 0, 0, 0);
        add(label, constraint);

        constraint.insets = new Insets(10, 0, 0, 0);
        add(stock, constraint);
        constraint.gridy = 2;
        add(wages, constraint);
        constraint.gridy = 3;
        add(expenses, constraint);
        constraint.gridy = 4;
        add(income, constraint);
        constraint.gridy = 5;
        add(create, constraint);
    }
}
