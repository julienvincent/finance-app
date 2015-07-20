/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin;

import applications.admin.components.Login;
import applications.resources.UIController;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;

public class Admin extends UIController {

    static JFrame frame;
    static Pane current;

    static Login login;

    public static void run() {

        frame = new JFrame();

        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);

        login = new Login();

//        this.swap(login);
//        login.setVisible(true);

//        frame.add(login);

        frame.setVisible(true);
    }

    public Admin() {

        super();
    }

    public static void main(String[] args) {

        run();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(52, 73, 94));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}