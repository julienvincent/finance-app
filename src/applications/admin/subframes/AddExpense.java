/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.admin.Admin;
import applications.admin.components.Expenses;
import applications.notify.Notify;
import applications.resources.Socket;
import applications.resources.UIController;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;
import applications.resources.components.ScrollPane;
import coms.EventsAdapter;
import helpers.Debug;
import models.Expense;
import socket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddExpense extends UIController {

    public static JFrame frame;

    /**
     * Start a new frame to add a new Expense.
     */
    public AddExpense() {

        super();

        frame = new JFrame();
        Pane pane = new Pane();

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label;
        Button add, cancel;
        TextField name, amount;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane() {

            setLayout(new GridBagLayout());

            label = new Label("Add an Expense");

            name = new TextField("Expense Name");
            amount = new TextField("Amount R 0.00");

            add = new Button("ADD", 14);
            cancel = new Button("CANCEL", 14);

            cancel.addActionListener(new ActionListener() {

                /**
                 * Close the Frame.
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            add.addActionListener(new ActionListener() {

                /**
                 * if validation was successful, create a new
                 * Expense instance of the users input and write
                 * the instance to the stream.
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!name.getText().isEmpty() && !amount.getText().isEmpty())
                        if (name.getText().matches("[a-zA-Z ]+"))
                            if (amount.getText().matches("[0-9]+"))
                                if (Admin.connected) {
                                    Expenses.Expense.action = "CREATE";
                                    Expenses.Expense.name = name.getText();
                                    Expenses.Expense.amount = Double.parseDouble(amount.getText());
                                    Admin.Socket.out(Expenses.Expense);

                                    frame.dispose();
                                } else
                                    new Notify("The socket server isn't running... Please start it and try again.");
                            else
                                new Notify("Please make sure the amount field is a number.");
                        else
                            new Notify("Name can only be letters!");
                    else
                        new Notify("Please fill in all the fields");
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

            add(label, constraint);

            constraint.insets = new Insets(0, 0, 10, 0);
            constraint.ipadx = 250;
            constraint.gridy = 1;
            add(name, constraint);
            constraint.gridy = 2;
            add(amount, constraint);

            constraint.ipadx = 0;
            constraint.insets = new Insets(0, 0, 30, 100);
            constraint.gridy = 3;
            add(add, constraint);
            constraint.insets = new Insets(0, 100, 30, 0);
            add(cancel, constraint);
        }

        /**
         * Paint a new color to the JFrame
         *
         * @param g Graphics
         */
        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(new Color(52, 73, 94));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
