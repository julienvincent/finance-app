/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package helpers;

public class Debug {

    /**
     * Debug with console colors (may need the grep console plugin)
     * @param m
     * @param c
     */
    public void debug(String m, String c) {

        c = c != null ? c : "DEFAULT";

        if (Files.logs())
            if (c.equals("DEFAULT"))
                System.out.println(m);
            else if (c.equals("ERROR"))
                System.out.println("\u001B[31m" + m);
            else if (c.equals("GREEN"))
                System.out.println("\u001B[32m" + m);
            else if (c.equals("BLUE"))
                System.out.println("\u001B[34m" + m);
    }
}
