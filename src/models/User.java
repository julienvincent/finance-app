/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import database.Query;
import helpers.Debug;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Model {

    ResultSet result;

    public String name;
    public String surname;
    public String email;
    public String password;

    public String error;

    public Boolean authorized = false;

    /**
     * Set the classes Identifier type-hint
     */
    public User() {

        super("user");
    }

    /**
     * Fire the AUTHORIZED event and pass itself as a parameter.
     */
    public void dispatch() {

        Dispatcher.userAuthorized(this);
    }

    /**
     * Authenticate the User Instance and return it's status
     * @return Boolean
     */
    public boolean auth() {

        result = new Query().query("SELECT * FROM users WHERE email = '" + email + "'");
        authorized = false;

        try {
            while (result.next()) {
                if (result.getInt("id") > 0)
                    if (result.getString("password").equals(password))
                        authorized = true;
                    else
                        error = "Incorrect Password";
            }
            if (!authorized && error == null)
                error = "Incorrect Email";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return authorized;
    }

    /**
     * Create a new User and authorize that user.
     * @return boolean
     */
    public boolean create() {

        new Query().query("INSERT INTO users (" +
                "name, surname, email, password, user_type) " +
                "VALUES (" +
                "'" + name + "', " +
                "'" + surname + "', " +
                "'" + email + "', " +
                "'" + password + "', " +
                "1)");

        return authorized = true;
    }
}
