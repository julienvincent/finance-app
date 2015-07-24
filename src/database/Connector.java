/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

import helpers.Debug;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    static Connection connection;
    Builder builder = new Builder();
    static Debug debug = new Debug();

    static String Name = "DevBase";
    static String User = "root";
    static String Password = "";
    static Boolean connected = false;

    public Connector() {

        try {

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            if (new File(Name).isDirectory()) {

                this.connect();
            } else {

                builder.build();
            }
        } catch (ClassNotFoundException ex) {

            debug.debug("Embedded driver is missing", "ERROR");
        }
    }

    public void connect() {

        try {

            Properties props = new Properties();
            props.setProperty("databaseName", Name);
            props.setProperty("user", User);
            props.setProperty("password", Password);

            connection = DriverManager.getConnection("jdbc:derby:", props);
            connected = true;

            debug.debug("Connected to database " + Name, "GREEN");
        } catch (SQLException ex) {

            debug.debug("Couldn't connect to database " + Name, "ERROR");
        }
    }

    public static Connection getConnection() {

        return connection;
    }

    public static void setConnection() {

        try {

            Properties props = new Properties();
            props.setProperty("databaseName", Name);
            props.setProperty("user", User);
            props.setProperty("password", Password);
            props.setProperty("create", "true");

            connection = DriverManager.getConnection("jdbc:derby:", props);
            connected = true;
        } catch (SQLException ex) {

            debug.debug("Couldn't connect to database " + Name, "ERROR");
        }
    }

    public static void main(String args[]) {

        new Connector();
    }
}
