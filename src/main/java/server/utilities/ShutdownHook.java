package server.utilities;

import server.models.ServerData;

import java.util.logging.Level;

/**
 * Shutdown class runs when the server is terminated:
 * Adds a message to the server log file & saves the data to disk.
 *
 * @author Michael Bragg
 */
public class ShutdownHook extends Thread {

    private final ServerData serverData;

    public ShutdownHook(ServerData serverData) {
        this.serverData = serverData;
    }

    /**
     * Run at shutdown. Writes the current of the data to disk
     * and writes a final shutdown message to the log.
     */
    @Override
    public void run() {
        LoggerWrapper.log(Level.INFO, "Server Shutdown");
        DB.write(serverData);
    }

}