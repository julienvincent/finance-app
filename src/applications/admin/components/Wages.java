/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.admin.components;

import applications.admin.Admin;
import applications.admin.subframes.EditWage;
import applications.resources.components.Button;
import applications.resources.components.Label;
import coms.EventsAdapter;
import helpers.Debug;
import models.Wage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wages extends JComponent {

    Label label, current;
    Double wage = 0.0;
    Button change, back;

    Debug debug = new Debug();
    public static Wage Wage = new Wage();

    /**
     * Instantiate new components and add action listeners to them
     */
    public Wages() {

        setLayout(new GridBagLayout());

        label = new Label("Wages");
        current = new Label("Fetching current wage from server.");

        change = new Button("CHANGE", 15);
        back = new Button("BACK", 15);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.frame.setSize(450, 400);
                Admin.home.setVisible(true);
            }
        });

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditWage editWage = new EditWage();
            }
        });

        new EventsAdapter() {

            @Override
            public void connected() {
                Wage.action = "GET";
                Admin.Socket.out(Wage);
            }

            @Override
            public void wageUpdated(Wage wages) {

                wage = wages.wage.doubleValue() / 100;
                current.setText("Wages are currently set at R" + wage / 100 + " per staff member.");
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
        constraint.gridwidth = 1;

        constraint.insets = new Insets(-20, 0, 0, 0);
        add(label, constraint);

        constraint.anchor = GridBagConstraints.CENTER;
        constraint.gridy = 1;
        constraint.insets = new Insets(0, 0, 100, 300);
        add(back, constraint);

        constraint.insets = new Insets(-60, 0, 0, 0);
        constraint.gridy = 2;
        add(current, constraint);

        constraint.insets = new Insets(10, 0, 30, 0);
        constraint.gridy = 3;
        add(change, constraint);
    }
}