package server.utilities;

import server.Factories.FileFactory;
import server.models.ServerData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

/**
 * Shutdown class runs when the server is terminated:
 * Adds a message to the server log file & saves the data to disk.
 *
 * @author Michael Bragg
 */
public class ShutdownHook extends Thread {
    private final ServerData serverData;
    private final String serverDataFileName;


    public ShutdownHook(ServerData serverData) {
        this.serverData = serverData;
        this.serverDataFileName = server.ServerLauncher.FILENAME;
    }

    /**
     * Run at shutdown. Writes the current of the data to disk
     * and writes a final shutdown message to the log.
     */
    @Override
    public void run() {
        LoggerWrapper.log(Level.INFO, "Server Shutdown");
        save();
    }

    /*
    Writes the current state for the data store to disk.
     */
    void save() {
        try (ObjectOutputStream stream = FileFactory.getInstance().getObjectOutputStream(serverDataFileName)) {
            stream.writeObject(serverData);
        } catch (IOException ex) {
            LoggerWrapper.log(Level.SEVERE, "Write error: " + ex);

        }
    }
}