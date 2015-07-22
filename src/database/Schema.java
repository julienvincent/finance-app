/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

public class Schema {

    /**
     * A schema for the databases tables
     * @return String[] schema
     */
    public String[] schema() {

        return new String[]{
                "CREATE TABLE users (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "name VARCHAR(30) NOT NULL, " +
                        "surname VARCHAR(30) NOT NULL, " +
                        "email VARCHAR(60) NOT NULL, " +
                        "password VARCHAR(60) NOT NULL, " +
                        "user_type INT NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")"
        };
    }
}