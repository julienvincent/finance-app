package models;

public class Dispatcher {

    private static Events event;

    /**
     * Register a class implementation as a callback
     * @param obj Callback
     */
    public Dispatcher(Events obj) {
        event = obj;
    }

    /**
     * Trigger all implementations of userAuthorized and pass the User instance
     * @param user User instance that was returned by the server.
     */
    public static void userAuthorized(User user) {
        event.userAuthorized(user);
    }
}