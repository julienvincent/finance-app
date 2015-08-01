/*
 |--------------------------------------------------------------------------
 | Admin Application
 |--------------------------------------------------------------------------
 **/

package applications.admin;

import applications.admin.components.*;
import applications.resources.UIController;
import applications.resources.Socket;
import coms.EventsAdapter;
import socket.Client;

import javax.swing.*;
import java.awt.*;

public class Admin {

    public static JFrame frame;
    static public Client Socket;

    public static Login login;
    public static Expenses expenses;
    public static Wages wages;
    public static Home home;
    public static Register register;
    public static Stock stock;
    public static Statement statement;

    public static Boolean connected = false;

    /**
     * Start the Admin JFrame and add build it's associated components
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

        new EventsAdapter() {

            @Override
            public void connected() {

                connected = true;
            }
        };

        frame.setVisible(true);
    }

    /**
     * Run the Admin application
     *
     * @param args args
     */
    public static void main(String[] args) {

        run();
    }

    public static class Swap extends UIController {

        /**
         * Add all UI components to the swap component
         */
        public Swap() {

            super();

            login = new Login();
            expenses = new Expenses();
            home = new Home();
            wages = new Wages();
            register = new Register();
            stock = new Stock();
            statement = new Statement();

            this.swap(login);
            this.swap(expenses);
            this.swap(home);
            this.swap(wages);
            this.swap(register);
            this.swap(stock);
            this.swap(statement);
            login.setVisible(true);
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