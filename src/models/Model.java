/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

import java.io.Serializable;

public class Model implements Serializable {

    public String model;

    /**
     * Set the models Instance type-hint
     * @param model Identifier
     */
    public Model(String model) {

        this.model = model;
    }
}
