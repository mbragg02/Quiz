package utilities;

import factories.FileFactory;
import models.ServerData;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Static methods to write and read data to disk
 *
 * @author Michael Bragg
 */
public class DB {

    public static final String FILENAME = "serverData.txt";


    public static ServerData read() {
        ServerData data = null;

        if (new File(FILENAME).exists()) {
            LoggerWrapper.log(Level.FINE, "Loading data from file: " + FILENAME);
            try (ObjectInputStream stream =  FileFactory.getInstance().getObjectInputStream(FILENAME)) {
                data = (ServerData) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LoggerWrapper.log(Level.WARNING, Arrays.toString(e.getStackTrace()));

            }
        } else {
            LoggerWrapper.log(Level.INFO, "Initialize server with zero quizzes/games");
            data = new ServerData();
        }

        return data;
    }

    public static void write(ServerData serverData) {

        try (ObjectOutputStream stream = FileFactory.getInstance().getObjectOutputStream(FILENAME)) {
            stream.writeObject(serverData);
        } catch (IOException ex) {
            LoggerWrapper.log(Level.SEVERE, "Write error: " + ex);
        }
    }
}
