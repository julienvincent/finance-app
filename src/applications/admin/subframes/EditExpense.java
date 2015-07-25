/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.admin.Admin;
import applications.admin.components.Expenses;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditExpense {

    public static JFrame frame;
    String name;

    public EditExpense(String name) {

        super();

        frame = new JFrame();
        Pane pane = new Pane();

        this.name = name;

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label;
        Button edit, cancel;
        TextField name, amount;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane() {

            setLayout(new GridBagLayout());

            label = new Label("Edit an Expense");

            name = new TextField();
            amount = new TextField();

            edit = new Button("ADD", 14);
            cancel = new Button("CANCEL", 14);

            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!name.getText().equals(""))
                        if (!amount.getText().equals("")) {
                            Expenses.Expense.action = "EDIT";
                            Expenses.Expense.name = name.getText();
                            Expenses.Expense.amount = Integer.parseInt(amount.getText());
                            Admin.Socket.out(Expenses.Expense);

                            frame.dispose();
                        }
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
