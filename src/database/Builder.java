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

    Statement statement;
    Seeder seeder = new Seeder();
    String[] schema = new Schema().schema();
    Debug debug = new Debug();

    String User = "root";
    String Password = "";

    /**
     * Create a database and build it's tables according to the Schema
     */
    public void build() {

        Connector.setConnection();

        try {

            statement = Connector.getConnection().createStatement();

            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.propertiesOnly', 'TRUE')");
            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user." + User + "', '" + Password + "')");
            statement.execute("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'TRUE')");

            for (String table : schema) {
                statement.executeUpdate(table);

                debug.debug("Created table " + table.substring(13, table.indexOf("(")), "BLUE");
            }

            statement.close();

            // if the schema implementation was successful, seed the database
            seeder.seed();
        } catch (SQLException e) {

            debug.debug("Could not build database", "ERROR");
            e.printStackTrace();
        }
    }
}
