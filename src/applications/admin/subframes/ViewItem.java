/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.admin.Admin;
import applications.resources.components.Button;
import applications.resources.components.Label;
import models.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewItem {

    public static JFrame frame;

    /**
     * Start a new Frame to view a specific item
     *
     * @param item Item instance
     */
    public ViewItem(Item item) {

        super();

        frame = new JFrame();
        Pane pane = new Pane(item);

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label, name, buyPrice, sellPrice, amount;
        Button close;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(Item item) {

            setLayout(new GridBagLayout());

            label = new Label("View order");

            name = new Label("Order name: " + item.name);
            buyPrice = new Label("Item buy price: " + item.buyPrice);
            sellPrice = new Label("Item sell price: " + item.sellPrice);
            amount = new Label("Current item stock: " + item.stock);

            close = new Button("CLOSE", 14);

            close.addActionListener(new ActionListener() {

                /**
                 * Close the Frame
                 *
                 * @param e Action clieck
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
            constraint.ipadx = 250;

            constraint.gridy = 1;
            add(name, constraint);

            constraint.gridy = 2;
            add(sellPrice, constraint);

            constraint.gridy = 3;
            add(buyPrice, constraint);

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
