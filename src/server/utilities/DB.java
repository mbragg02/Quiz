package server.utilities;

import server.factories.FileFactory;
import server.models.ServerData;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Sttaic methods to write and read data to disk
 *
 * @author Michael Bragg
 */
public class DB {

    public static ServerData read(String fileName) {
        ServerData data = null;

        if (new File(fileName).exists()) {
            LoggerWrapper.log(Level.FINE, "Loading data from file: " + fileName);
            try (ObjectInputStream stream =  FileFactory.getInstance().getObjectInputStream(fileName)) {
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
        String serverDataFileName = server.ServerLauncher.FILENAME;

        try (ObjectOutputStream stream = FileFactory.getInstance().getObjectOutputStream(serverDataFileName)) {
            stream.writeObject(serverData);
        } catch (IOException ex) {
            LoggerWrapper.log(Level.SEVERE, "Write error: " + ex);
        }
    }
}
