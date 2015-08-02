/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import coms.Dispatcher;
import database.Query;
import helpers.Debug;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Wage model for mutations
 */
public class Wage extends Model {

    ResultSet result;

    public Integer wage;
    public Double newWage;

    /**
     * Set the classes Identifier type-hint
     */
    public Wage() {

        super("wage");
    }

    /**
     * Fire the wageUpdated event to update all listening Components.
     */
    @Override
    public void dispatch() {

        Dispatcher.wageUpdated(this);
    }

    /**
     * Get the current wage from the database.
     *
     * @return Boolean was successful.
     */
    public boolean get() {

        result = new Query().query("SELECT * FROM wages");
        wage = null;

        try {
            while (result.next()) {
                wage = result.getInt("wage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = null;

        return true;
    }

    /**
     * Update the specified wage.
     *
     * @return Boolean was successful.
     */
    public boolean update() {

        newWage *= 100;
        get();

        if (wage != null)
            new Query().query("UPDATE wages SET wage=" + newWage.intValue() + " WHERE id = 1");
        else
            new Query().query("INSERT INTO wages (wage) VALUES " + newWage.intValue());

        dispatch();

        return true;
    }
}
