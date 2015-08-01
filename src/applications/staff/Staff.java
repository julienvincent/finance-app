/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.staff;

import applications.staff.components.Login;
import applications.resources.Socket;
import applications.resources.UIController;
import applications.staff.components.OrderManager;
import coms.Dispatcher;
import coms.Events;
import coms.EventsAdapter;
import models.Order;
import models.User;
import socket.Client;

import javax.swing.*;
import java.awt.*;

public class Staff {

    static JFrame frame;
    static public Client Socket;

    public static Boolean connected = false;

    public static Login login;
    public static OrderManager manager;

    /**
     * Start the Staff JFrame and build it's associated components
     */
    public static void run() {

        frame = new JFrame();
        Swap swap = new Swap();

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(swap);

        Socket = new Client();
        Thread socket = new Thread(new Socket(Socket));
        socket.start();

        frame.setVisible(true);

        new EventsAdapter() {
            @Override
            public void connected() {

                connected = true;
            }
        };
    }

    /**
     * Run the application
     * @param args args
     */
    public static void main(String[] args) {

        run();
    }

    public static class Swap extends UIController {

        /**
         * Add all the demo component to the swap pane and make it visible
         */
        public Swap() {

            super();

            login = new Login();
            manager = new OrderManager();

            this.swap(login);
            this.swap(manager);

            login.setVisible(true);

        }

        /**
         * Paint a new color to the JFrame
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
