/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.notify;

import applications.admin.Admin;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.ScrollPane;
import applications.resources.components.TextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A frame to notify the user of errors.
 */
public class Notify {

    public static JFrame frame;

    public Notify(String m) {

        super();

        frame = new JFrame();
        Pane pane = new Pane(m);

        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        frame.add(pane);
        frame.setVisible(true);
    }

    public final class Pane extends JComponent {

        Button ok;
        TextArea message;

        /**
         * Instantiate new components and add action listeners to them
         */
        public Pane(String m) {

            setLayout(new GridBagLayout());

            ok = new Button("OK", 15);
            message = new TextArea();

            ok.addActionListener(new ActionListener() {

                /**
                 * Close this frame
                 *
                 * @param e Action click
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            build();

            message.append(m);
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

            constraint.ipadx = 250;
            constraint.ipady = 125;
            constraint.insets = new Insets(20, 0, 15, 0);
            add(new ScrollPane(message), constraint);

            constraint.gridy = 1;
            constraint.ipadx = 0;
            constraint.ipady = 0;
            constraint.insets = new Insets(0, 0, 20, 0);
            add(ok, constraint);
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
