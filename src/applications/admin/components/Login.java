/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import javax.swing.*;
import java.awt.*;

public class Login extends JComponent {

    Button button;

    public Login() {

        setLayout(new GridBagLayout());
//        setPreferredSize(new Dimension(600, 610));

        button = new Button("Some Text");

    }

    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        add(button, constraint);
    }
}
