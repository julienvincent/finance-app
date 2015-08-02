/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.resources.components.Button;
import applications.resources.components.Label;
import models.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewExpense {

    public static JFrame frame;

    /**
     * Start a new Frame to view a selected expense
     *
     * @param expense Expense instance
     */
    public ViewExpense(Expense expense) {

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

        Label label, name, amount;
        Button close;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(Expense expense) {

            setLayout(new GridBagLayout());

            label = new Label("View Expense");

            name = new Label("Expense name: " + expense.name);
            amount = new Label("Current expense amount: R" + expense.amount / 100);

            close = new Button("CLOSE", 14);

            close.addActionListener(new ActionListener() {

                /**
                 * Close the Frame
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
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
            constraint.ipadx = 0;

            constraint.gridy = 1;
            add(name, constraint);

            constraint.gridy = 4;
            add(amount, constraint);

            constraint.gridy = 5;
            constraint.ipadx = 0;
            constraint.ipady = 0;
            add(close, constraint);
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
