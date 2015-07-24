/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package helpers;

import applications.resources.components.TextArea;

import java.awt.*;

public class Debug {

    /**
     * Debug to the console with custom colors, and print to the provided Text Area.
     * @param m message
     * @param c color
     * @param logs Text Area
     */
    public void debug(String m, String c, TextArea logs) {

        logs.append("\n" + m);
        if (Files.logs())
            if (c.equals("ERROR"))
                System.out.println("\u001B[31m" + m);
            else if (c.equals("GREEN"))
                System.out.println("\u001B[32m" + m);
            else if (c.equals("BLUE"))
                System.out.println("\u001B[34m" + m);
    }

    /**
     * Debug to the console.
     * @param m message
     */
    public void debug(String m) {
        if (Files.logs())
            System.out.println("\u001B[36m" + m);
    }

    /**
     * Debug to the console and print to the provided Text Area
     * @param m message
     * @param logs Text Area
     */
    public void debug(String m, TextArea logs) {

        logs.append("\n" + m);
        if (Files.logs())
            System.out.println("\u001B[36m" + m);
    }

    /**
     * Debug to the console with custom colors
     * @param m message
     * @param c color
     */
    public void debug(String m, String c) {

        if (Files.logs())
            if (c.equals("ERROR"))
                System.out.println("\u001B[31m" + m);
            else if (c.equals("GREEN"))
                System.out.println("\u001B[32m" + m);
            else if (c.equals("BLUE"))
                System.out.println("\u001B[34m" + m);
    }
}
