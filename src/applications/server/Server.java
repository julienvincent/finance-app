/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.server;

import applications.resources.UIController;
import applications.server.components.Logs;

import javax.swing.*;
import java.awt.*;

public class Server {

    static JFrame frame;

    public static void run() {

        frame = new JFrame();
        Swap swap = new Swap();

        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(swap);

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        run();
    }
}

class Swap extends UIController {

    static Logs logs;

    public Swap() {

        super();

        logs = new Logs();

        this.swap(logs);
        logs.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(52, 73, 94));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}