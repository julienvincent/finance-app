/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.subframes;

import applications.admin.Admin;
import applications.notify.Notify;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;
import models.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItem {

    public static JFrame frame;

    /**
     * Start a new Frame to edit the selected item
     *
     * @param item Item instance
     */
    public EditItem(Item item) {

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

        Label label;
        Button edit, cancel;
        TextField name, buyPrice, sellPrice, stock;
        Item item;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(Item item) {

            this.item = item;

            setLayout(new GridBagLayout());

            label = new Label("Edit an Expense");

            buyPrice = new TextField("Buy Price (" + item.buyPrice / 100 + ")");
            sellPrice = new TextField("Sell Price (" + item.sellPrice / 100 + ")");
            stock = new TextField("amount (" + item.stock + ")");

            edit = new Button("EDIT", 14);
            cancel = new Button("CANCEL", 14);

            cancel.addActionListener(new ActionListener() {

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

            edit.addActionListener(new ActionListener() {

                /**
                 * If validation was successful,
                 * update the item instance with the users input
                 * and write it to the stream
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {

                    Boolean run = true;
                    if (!buyPrice.getText().isEmpty())
                        if (buyPrice.getText().matches("^\\d+(\\.\\d{1,2})?$"))
                            item.buyPrice = Double.parseDouble(buyPrice.getText());
                        else {
                            new Notify("Buy price must be an integer");
                            run = false;
                        }

                    if (!sellPrice.getText().isEmpty())
                        if (sellPrice.getText().matches("^\\d+(\\.\\d{1,2})?$"))
                            item.sellPrice = Double.parseDouble(sellPrice.getText());
                        else {
                            run = false;
                            new Notify("Sell price must be an integer");
                        }

                    if (!stock.getText().isEmpty())
                        if (stock.getText().matches("[0-9]+"))
                            item.stock = Integer.parseInt(stock.getText());
                        else {
                            run = false;
                            new Notify("Stock must be an integer");
                        }

                    if (run)
                        if (Admin.connected) {
                            item.action = "EDIT";
                            Admin.Socket.out(item);
                            frame.dispose();
                        } else
                            new Notify("The Socket Server isn't running... Please start it and try again");
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
            constraint.gridy = 2;
            add(sellPrice, constraint);
            constraint.gridy = 3;
            add(buyPrice, constraint);
            constraint.gridy = 4;
            add(stock, constraint);

            constraint.ipadx = 0;
            constraint.insets = new Insets(0, 0, 30, 100);
            constraint.gridy = 5;
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