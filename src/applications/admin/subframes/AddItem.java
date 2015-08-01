package applications.admin.subframes;

import applications.admin.Admin;
import applications.admin.components.Expenses;
import applications.admin.components.Stock;
import applications.notify.Notify;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

import models.Item;

public class AddItem {

    public static JFrame frame;

    /**
     * Start a new frame to add a new Item.
     */
    public AddItem() {

        super();

        frame = new JFrame();
        Pane pane = new Pane();

        frame.setSize(450, 500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label;
        Button add, cancel;
        TextField name, stock, buy, sell;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane() {

            setLayout(new GridBagLayout());

            label = new Label("Add an Item");

            name = new TextField("Item Name");
            stock = new TextField("Stock");
            buy = new TextField("Buy Price");
            sell = new TextField("Sell Price");

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
                 * If validation was successful, update the item instance
                 * with the users provided information and write
                 * the instance to the stream.
                 *
                 * @param e Action click.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!name.getText().isEmpty() &&
                            !stock.getText().isEmpty() &&
                            !buy.getText().isEmpty() &&
                            !sell.getText().isEmpty())
                        if (name.getText().matches("[a-zA-Z ]+"))
                            if (stock.getText().matches("[0-9]+"))
                                if (buy.getText().matches("^\\d+(\\.\\d{1,2})?$"))
                                    if (sell.getText().matches("^\\d+(\\.\\d{1,2})?$"))
                                        if (Admin.connected) {
                                            Stock.Item.action = "CREATE";
                                            Stock.Item.name = name.getText();
                                            Stock.Item.stock = Integer.parseInt(stock.getText());
                                            Stock.Item.buyPrice = Double.parseDouble(buy.getText());
                                            Stock.Item.sellPrice = Double.parseDouble(sell.getText());
                                            Admin.Socket.out(Stock.Item);

                                            frame.dispose();
                                        } else
                                            new Notify("The socket server isn't running... Please start it and try again.");
                                    else
                                        new Notify("Please enter a valid sell price");
                                else
                                    new Notify("Please enter a valid buy price");
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
            add(stock, constraint);
            constraint.gridy = 3;
            add(buy, constraint);
            constraint.gridy = 4;
            add(sell, constraint);

            constraint.ipadx = 0;
            constraint.insets = new Insets(0, 0, 30, 100);
            constraint.gridy = 5;
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