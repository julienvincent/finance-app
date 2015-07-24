/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package models;

public interface Events {

    /**
     * Fired when a user is Authorized
     * @param user User instance returned by the server.
     */
    public void userAuthorized(User user);
}
