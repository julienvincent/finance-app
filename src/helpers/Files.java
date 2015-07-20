/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package helpers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files {

    /**
     * Read from the config file and determine if logs are enabled
     * @return Boolean logs
     */
    public static Boolean logs() {

        Path path = Paths.get("./system.conf");
        Boolean logs = true;

        try {
            if (java.nio.file.Files.exists(path)) {
                for (String line : java.nio.file.Files.readAllLines(path)) {
                    if (line.substring(0, line.indexOf('=')).equals("logs")) {
                        logs = Boolean.parseBoolean(line.substring(line.indexOf("=") + 1));
                    } else {
                        java.nio.file.Files.write(path, "logs=true".getBytes());
                    }
                }
            } else {
                java.nio.file.Files.write(path, "logs=true".getBytes());
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return logs;
    }
}
