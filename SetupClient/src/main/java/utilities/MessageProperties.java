package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by Michael Bragg on 10/04/2014.
 *
 */
public class MessageProperties {

    private static Properties props;

    public static String msg(String prop) {
        if(props == null) {
            loadPropertiesFile();
        }
        return props.getProperty(prop);
    }

    private static void loadPropertiesFile() {
        try {
            try (InputStream in = new FileInputStream("SetupClient/messages.properties")) {
                props = new Properties();
                props.load(in);
            }
        } catch (IOException e) {
            utilities.LoggerWrapper.log(Level.SEVERE, e.getMessage());
        }
    }
}
