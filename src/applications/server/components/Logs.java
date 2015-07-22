/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.server.components;

import applications.resources.components.*;
import applications.resources.components.Button;
import applications.resources.components.ScrollPane;
import applications.resources.components.TextArea;
import helpers.Debug;
import socket.Server;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Logs extends JComponent {

    Button button;
    TextArea logs;

    Debug debug = new Debug();
    Server server = new Server();

    public Logs() {

        setLayout(new GridBagLayout());

        button = new Button("START", null);
        logs = new TextArea();

        Thread socket = new Thread(new Socket(logs));
        socket.start();
        build();
    }

    public void build() {

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridheight = 1;
        constraint.gridwidth = 1;

        constraint.gridx = 1;
        constraint.ipadx = 300;
        constraint.ipady = 130;
        add(new ScrollPane(logs), constraint);
    }
}
