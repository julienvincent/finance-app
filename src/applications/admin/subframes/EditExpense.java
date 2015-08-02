/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.admin.Admin;
import applications.admin.components.Expenses;
import applications.notify.Notify;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;
import models.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditExpense {

    public static JFrame frame;

    /**
     * Start a new frame to edit the selected Expense.
     *
     * @param expense Expense instance
     */
    public EditExpense(Expense expense) {

        super();

        frame = new JFrame();
        Pane pane = new Pane(expense);

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label;
        Button edit, cancel;
        TextField amount;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(Expense expense) {

            setLayout(new GridBagLayout());

            label = new Label("Edit an Expense");

            amount = new TextField("Amount (" + expense.amount / 100 + ")");

            edit = new Button("EDIT", 14);
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

            edit.addActionListener(new ActionListener() {

                /**
                 * If validation was successful, update
                 * the item instance with the users input
                 * and write the instance to the stream.
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {

                    Boolean run = true;
                    if (!amount.getText().isEmpty())
                        if (amount.getText().matches("[0-9]+"))
                            expense.amount = Double.parseDouble(amount.getText());
                        else {
                            new Notify("Amount must be an integer");
                            run = false;
                        }
                    if (run)
                        if (Admin.connected) {
                            expense.action = "EDIT";
                            Admin.Socket.out(expense);

                            frame.dispose();
                        } else
                            new Notify("The socket server isn't running... Please start it and try again.");
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
            constraint.gridy = 2;
            add(amount, constraint);

            constraint.ipadx = 0;
            constraint.insets = new Insets(0, 0, 30, 100);
            constraint.gridy = 3;
            add(edit, constraint);
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
