/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.staff.components;

import applications.notify.Notify;
import applications.staff.Staff;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.PasswordField;
import applications.resources.components.TextField;
import coms.EventsAdapter;
import helpers.Debug;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Login extends JComponent {

    Button login;
    TextField email;
    PasswordField password;
    public Label label;

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
             * Run validation on the user input
             *
             * - Set this User instance's action to AUTH
             * and write the instance to the stream.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!email.getText().equals(""))
                    if (!password.getText().equals("")) {
                        User.action = "AUTH";
                        User.email = email.getText();
                        User.password = password.getText();
                        User.userType = 0;
                        Staff.Socket.out(User);
                    } else {
                        new Notify("Please fill in all the fields");
                    }
                else
                    new Notify("Please fill in all the fields");
            }
        });

        new EventsAdapter() {

            /**
             * Swap the UI if the AUTH process was successful.
             * @param user User instance returned by the server
             */
            @Override
            public void auth(User user) {

                if (user.authorized)
                    Staff.manager.setVisible(true);
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
