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
import applications.notify.Notify;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextField;
import coms.EventsAdapter;
import helpers.Debug;
import coms.Events;
import models.Order;
import models.User;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import coms.Dispatcher;

public final class Login extends JComponent {

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

        setLayout(new GridBagLayout());

        login = new Button("LOGIN", null);
        email = new TextField("Email");
        password = new PasswordField("Password");
        label = new Label("");

        login.addActionListener(new ActionListener() {

            /**
             * If all fields are filled in, create a new user
             * instance with the AUTH action and write it to
             * the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!email.getText().isEmpty())
                    if (!password.getText().isEmpty())
                        if (Admin.connected) {
                            User.action = "AUTH";
                            User.email = email.getText();
                            User.password = password.getText();
                            User.userType = 1;
                            Admin.Socket.out(User);
                        } else
                            new Notify("The socket server isn't running... Please start it and try again.");
                    else
                        new Notify("Password is not filled in.");
                else
                    new Notify("Email is not filled in.");
            }
        });

        new EventsAdapter() {

            /**
             * If the user instance is authorized,
             * launch the home component.
             *
             * @param user User instance returned by the server.
             */
            @Override
            public void auth(User user) {
                if (user.authorized)
                    Admin.home.setVisible(true);
                else
                    new Notify(user.error);
            }
        };

        build();
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
