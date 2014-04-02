package server.utilities;


import server.Factories.FileFactory;
import server.models.ServerData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 * Shutdown class runs when the server is terminated:
 * Adds a message to the server log file & saves the data to disk.
 * @author Michael Bragg
 */
public class ShutdownHook extends Thread {
	private final Logger logger;
    private ServerData serverData;
    private String serverDataFileName;


    public ShutdownHook(Logger logger, ServerData serverData) {
		this.logger = logger;
        this.serverData = serverData;
        this.serverDataFileName = server.ServerLauncher.FILENAME;
	}

	@Override
	public void run()
    {
        logger.info("Server Shutdown");
        save();
    }

    void save() {
        try (ObjectOutputStream stream = FileFactory.getInstance().getObjectOutputStream(serverDataFileName)) {
            stream.writeObject(serverData);
        } catch (IOException ex) {
            logger.warning("Write error: " + ex);
        }
    }
}