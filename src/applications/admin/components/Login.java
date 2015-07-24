/*
 |--------------------------------------------------------------------------
 | Login interface.
 |
 | - Matches user credentials with the database.
 | - If a match is made, Swap out this component with the Manager
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;
import helpers.Debug;
import models.Events;
import models.User;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Dispatcher;

public final class Login extends JComponent implements Events {

    private Dispatcher dispatcher;
    
    Button login;
    TextField email;
    PasswordField password;
    Label label;

    User User = new User();

    Debug debug = new Debug();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Login() {
        
        dispatcher = new Dispatcher(this);

        setLayout(new GridBagLayout());

        login = new Button("LOGIN", null);
        email = new TextField();
        password = new PasswordField();
        label = new Label("");

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!email.getText().equals(""))
                if (!password.getText().equals("")) {
                    User.email = email.getText();
                    User.password = password.getText();
                    Admin.Socket.out(User);
                }
            }
        });

        build();
    }

    /**
     * Handle the returned User instance
     * @param user User instance returned by the server.
     */
    @Override
    public void userAuthorized(User user) {

        if (user.authorized)
            debug.debug("AUTHORIZED");
        else
            label.setText(user.error);
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
        constraint.gridwidth = 2;

        constraint.ipadx = 250;
        add(email, constraint);
        constraint.insets = new Insets(10, 0, 0, 0);
        constraint.gridy = 1;
        add(password, constraint);
        constraint.gridy = 2;
        constraint.ipadx = 100;
        constraint.ipady = 15;
        add(login, constraint);

        constraint.anchor = GridBagConstraints.PAGE_END;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.weightx = 5;
        constraint.gridy = 3;
        constraint.ipadx = 0;
        constraint.ipady = 0;
        add(label, constraint);
    }
}
