package server.utilities;

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
            //URL url = getClass().getResource("messages.properties");
            //System.out.println(url);
            try (InputStream in = new FileInputStream("messages.properties")) {
                props = new Properties();
                props.load(in);
            }
        } catch (IOException e) {
            LoggerWrapper.log(Level.SEVERE, e.getMessage());
        }
    }
}
