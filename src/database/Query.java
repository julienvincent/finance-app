/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

import helpers.Debug;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

    Statement statement;
    ResultSet result;
    Debug debug = new Debug();

    public ResultSet query(String query) {

        new Connector().connect();
        if (Connector.connected) {

            try {
                statement = Connector.getConnection().createStatement();

                if (query.substring(0, 6).equals("SELECT"))
                    result = statement.executeQuery(query);
                else
                    statement.executeUpdate(query);

                debug.debug("Completed query: " + query);
            } catch (SQLException e) {
                debug.debug("SQL Execution failed: " + e.getMessage(), "ERROR");
            }
        } else {
            query(query);
        }

        return result;
    }
}
