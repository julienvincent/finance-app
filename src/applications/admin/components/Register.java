/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.notify.Notify;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.Label;
import applications.resources.components.TextArea;
import applications.resources.components.TextField;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JComponent {

    Label label;
    TextField name, surname, email;
    PasswordField password, confirmationPassword;
    Button register, back;

    User User = new User();

    public Register() {

        setLayout(new GridBagLayout());

        label = new Label("Register a new staff Member");

        name = new TextField("Name");
        surname = new TextField("Surname");
        email = new TextField("Email");
        password = new PasswordField("Password");
        confirmationPassword = new PasswordField("Confirm Password");

        back = new Button("BACK", 15);
        register = new Button("REGISTER", 15);

        register.addActionListener(new ActionListener() {

            /**
             * If validation was successful, create a new user instance
             * with the users input and write it to the stream
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!name.getText().isEmpty() &&
                        !surname.getText().isEmpty() &&
                        !email.getText().isEmpty() &&
                        !password.getText().isEmpty() &&
                        !confirmationPassword.getText().isEmpty())
                    if (name.getText().matches("[a-zA-Z ]+"))
                        if (surname.getText().matches("[a-zA-Z ]+"))
                            if (email.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
                                if (password.getText().length() >= 6)
                                    if (confirmationPassword.getText().equals(password.getText()))
                                        if (Admin.connected) {
                                            User.action = "CREATE";
                                            User.userType = 0;
                                            User.name = name.getText();
                                            User.surname = surname.getText();
                                            User.email = email.getText();
                                            User.password = password.getText();
                                            Admin.Socket.out(User);

                                            Admin.frame.setSize(450, 350);
                                            Admin.home.setVisible(true);
                                        } else
                                            new Notify("The socket server isn't running... Please start it and try again");
                                    else
                                        new Notify("Your passwords do not match!");
                                else
                                    new Notify("Password must be at least 6 characters!");
                            else
                                new Notify("Please provide a valid email address");
                        else
                            new Notify("Surname must be only letters!");
                    else
                        new Notify("Name must be only letters!");
                else
                    new Notify("Please fill in all the fields");

            }
        });

        back.addActionListener(new ActionListener() {

            /**
             * Return to the Home component.
             *
             * @param e Action click
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 350);
                Admin.home.setVisible(true);
            }
        });

        build();
    }

    /**
     * Layout of this Components sub-components
     */
    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;

        constraint.insets = new Insets(0, 0, 50, 300);
        add(back, constraint);

        constraint.anchor = GridBagConstraints.CENTER;
        constraint.insets = new Insets(10, 0, 0, 0);
        constraint.gridy = 1;
        constraint.ipadx = 250;
        add(name, constraint);

        constraint.gridy = 2;
        add(surname, constraint);

        constraint.gridy = 3;
        add(email, constraint);

        constraint.gridy = 4;
        add(password, constraint);

        constraint.gridy = 5;
        add(confirmationPassword, constraint);

        constraint.gridy = 6;
        constraint.ipadx = 0;
        constraint.ipady = 0;
        add(register, constraint);
    }
}
