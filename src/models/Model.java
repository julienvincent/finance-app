/*
 |--------------------------------------------------------------------------
 | Model base.
 | Is serializable.
 |--------------------------------------------------------------------------
 **/

package models;

import java.io.Serializable;

/**
 * Is serializable to allow writing over Streams.
 */
public class Model implements Serializable {

    public String model;
    public String action;

    /**
     * Set the models Instance type-hint
     * @param model Identifier
     */
    public Model(String model) {

        this.model = model;
    }
}
