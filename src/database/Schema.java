/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package database;

public class Schema {

    /**
     * A schema for building the databases' tables
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
                        ")",

                "CREATE TABLE items (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "name VARCHAR(30) NOT NULL, " +
                        "sell_price INT NOT NULL, " +
                        "buy_price INT NOT NULL, " +
                        "stock INT NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")",

                "CREATE TABLE orders (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "code VARCHAR(10) NOT NULL, " +
                        "user_id INT, " +
                        "completed BOOLEAN NOT NULL, " +
                        "canceled BOOLEAN NOT NULL, " +
                        "FOREIGN KEY (user_id) REFERENCES users (id), " +
                        "PRIMARY KEY (code)" +
                        ")",

                "CREATE TABLE ordered_items (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "order_code VARCHAR(10), " +
                        "item_id INT, " +
                        "amount INT NOT NULL, " +
                        "FOREIGN KEY (order_code) REFERENCES orders (code), " +
                        "FOREIGN KEY (item_id) REFERENCES items (id), " +
                        "PRIMARY KEY (id)" +
                        ")",

                "CREATE TABLE expenses (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "name VARCHAR(30) NOT NULL, " +
                        "amount INT NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")",

                "CREATE TABLE wages (" +
                        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "wage INT NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")",
        };
    }
}