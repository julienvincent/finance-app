/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JComponent {

    Label label;
    Button register, back;

    public Register() {

        setLayout(new GridBagLayout());

        label = new Label("Register a new staff Member");

        back = new Button("BACK", 15);
        register = new Button("REGISTER", 15);

        back.addActionListener(new ActionListener() {
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

        constraint.insets = new Insets(0, 0, 100, 300);
        add(back, constraint);
    }
}
