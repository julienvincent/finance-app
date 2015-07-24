/*
 |--------------------------------------------------------------------------
 | Admin Application
 |--------------------------------------------------------------------------
 **/

package applications.admin;

import applications.admin.components.Login;
import applications.resources.UIController;
import applications.resources.Socket;
import socket.Client;

import javax.swing.*;
import java.awt.*;

public class Admin {

    static JFrame frame;
    static public Client Socket;

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

        frame.setVisible(true);
    }

    /**
     * Run the Admin application
     * @param args args
     */
    public static void main(String[] args) {

        run();
    }
}

class Swap extends UIController {
    
    static Login login;

    /**
     * Add all UI components to the swap component
     */
    public Swap() {
        
        super();
        
        login = new Login();
        
        this.swap(login);
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