/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

import helpers.Debug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Builder {

    Connection connection;
    Statement statement;
    Seeder seeder = new Seeder();
    String[] schema = new Schema().schema();
    Debug debug = new Debug();

    String Name = "DevBase";
    String User = "root";
    String Password = "";

    /**
     * Create a database and build it's tables according to the Schema
     */
    public void build() {

        connect();

        try {

            statement = connection.createStatement();

            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.propertiesOnly', 'TRUE')");
            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user." + User + "', '" + Password + "')");
            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'TRUE')");

            for (String table : schema) {
                statement.executeUpdate(table);

                // Logs
                debug.debug("Created table " + table.substring(13, table.indexOf("(")), "BLUE");
            }

            statement.close();

            // if the schema implementation was successful, seed the database
            seeder.seed();
        } catch (SQLException e) {

            debug.debug("Could not build database", "ERROR");
        }
    }

    /**
     * Attempt to create a connection, setting the Connectors instance to the connection
     * on success
     */
    private void connect() {

        Properties props = new Properties();
        props.setProperty("databaseName", Name);
        props.setProperty("user", User);
        props.setProperty("password", Password);
        props.setProperty("create", "true");

        try {

            connection = DriverManager.getConnection("jdbc:derby:", props);

            // logs
            debug.debug("Created database " + Name, "BLUE");
            debug.debug("Connected to database " + Name, "GREEN");
        } catch (SQLException ex) {

            debug.debug("Unable to create database " + Name, "ERROR");
        }
    }
}
