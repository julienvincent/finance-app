/*
 |--------------------------------------------------------------------------
 | Model base.
 | Is serializable.
 |--------------------------------------------------------------------------
 **/

package models;

import java.io.Serializable;

/**
 * Model Parent class, is serializable for
 * stream communication
 */
public class Model implements Serializable {

    public String model;
    public String action;

    /**
     * Set the models Instance type-hint
     *
     * @param model Identifier
     */
    public Model(String model) {

        this.model = model;
    }

    /**
     * Parent dispatch method.
     * - Allows calling of model dispatchers without needing to know to whom the instance belongs.
     */
    public void dispatch() {
        System.out.println("over here");
    }

    /**
     * Parent dispatch method with a action string.
     * - Allows calling of model dispatchers without needing to know to whom the instance belongs.
     */
    public void dispatch(String action) {

    }
}
