package applications.staff.components;

import applications.resources.components.*;
import applications.staff.Staff;
import coms.EventsAdapter;
import models.Order;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ViewOrder {

    public static JFrame frame;

    /**
     * Create a new Frame to view orders
     *
     * @param order Order instance.
     */
    public ViewOrder(Order order) {

        super();

        frame = new JFrame();
        Pane pane = new Pane(order);

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Label label, name;
        Button close;
        List list;
        ScrollPane scroll;

        ArrayList<String> items = new ArrayList<>();

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(Order order) {

            setLayout(new GridBagLayout());

            label = new Label("View Order");

            name = new Label("Order name: " + order.code);

            list = new List();
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            list.setVisibleRowCount(-1);
            scroll = new ScrollPane(list);

            close = new Button("CLOSE", 14);

            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });


            System.out.println("sent action");
            order.action = "ITEMS";
            Staff.Socket.out(order);


            new EventsAdapter() {

                /**
                 * Update the list of orders.
                 *
                 * @param order Order instance returned by the server.
                 */
                @Override
                public void orderedItemsUpdated(Order order) {

                    items = new ArrayList<>();
                    for (String item : order.items)
                        items.add(item);
                    list.setListData(items.toArray());
                }
            };

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

            constraint.anchor = GridBagConstraints.CENTER;
            constraint.gridy = 2;
            constraint.ipadx = 300;
            constraint.ipady = 130;
            add(scroll, constraint);

            constraint.gridy = 3;
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
