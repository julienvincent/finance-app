/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin;

import applications.admin.components.Login;
import applications.resources.UIController;

import javax.swing.*;
import java.awt.*;

public class Admin {

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
    
    static Login login;
    
    public Swap() {
        
        super();
        
        login = new Login();
        
        this.swap(login);
        login.setVisible(true);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(52, 73, 94));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}