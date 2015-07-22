/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/
package applications.admin.components;

import applications.resources.components.Button;
import applications.resources.components.TextField;
import helpers.Debug;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Login extends JComponent {

    Button login;
    TextField email, password;

    Debug debug = new Debug();

    public Login() {

        setLayout(new GridBagLayout());

        login = new Button("LOGIN", null);
        email = new TextField();
        password = new TextField();

        login.addActionListener(e -> debug.debug("CLICKED"));

        build();

    }

    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 2;

        constraint.ipadx = 250;
        add(email, constraint);
        constraint.insets = new Insets(10, 0, 0, 0);
        constraint.gridy = 1;
        add(password, constraint);
        constraint.gridy = 2;
        add(login, constraint);
    }
}
