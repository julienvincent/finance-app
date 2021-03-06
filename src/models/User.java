/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import coms.Dispatcher;
import database.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends Model {

    ResultSet result;

    public String name;
    public String surname;
    public String email;
    public String password;
    public Integer userType;

    public String error;

    public Boolean authorized = false;

    public ArrayList<User> users = new ArrayList<>();

    /**
     * Set the classes Identifier type-hint
     */
    public User() {

        super("user");
    }

    /**
     * Fire the AUTHORIZED event and pass itself as a parameter.
     */
    @Override
    public void dispatch() {

        Dispatcher.auth(this);
    }

    @Override
    public void dispatch(String action) {

        Dispatcher.usersUpdated(this);
    }

    /**
     * Authenticate the provided User
     *
     * @return Boolean was successful.
     */
    public boolean auth() {

        result = new Query().query("SELECT * FROM users WHERE email = '" + email + "'");
        authorized = false;

        try {
            while (result.next()) {
                if (result.getInt("id") > 0)
                    if (result.getString("password").equals(password))
                        if (result.getInt("user_type") >= userType)
                            authorized = true;
                        else
                            error = "Your user does not have the correct privileges";
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
     * Create a new user and log that user in.
     *
     * @return boolean was successful.
     */
    public boolean create() {

        new Query().query("INSERT INTO users (" +
                "name, surname, email, password, user_type) " +
                "VALUES (" +
                "'" + name + "', " +
                "'" + surname + "', " +
                "'" + email + "', " +
                "'" + password + "', " +
                userType + ")");

        return authorized = true;
    }


        public Boolean getAll() {

            result = new Query().query("SELECT * FROM users");
            users = new ArrayList<>();

            try {
                while (result.next()) {
                    User user = new User();
                    user.name = result.getString("name");
                    user.surname = result.getString("surname");
                    user.userType = result.getInt("user_type");
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            result = null;

            return true;
        }

}
