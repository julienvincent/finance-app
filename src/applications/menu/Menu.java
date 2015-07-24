/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.menu;

import applications.menu.components.Demo;
import applications.resources.Socket;
import applications.resources.UIController;
import socket.Client;

import javax.swing.*;
import java.awt.*;

public class Menu {

    static JFrame frame;
    static public Client Socket;

    /**
     * Start the Menu JFrame and add build it's associated components
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

    static Demo demo;

    /**
     * Add all the demo component to the swap pane and make it visible
     */
    public Swap() {

        super();

        demo = new Demo();

        this.swap(demo);
        demo.setVisible(true);

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
