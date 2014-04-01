package server.utilities;


import server.models.Data;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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
    private Data data;
    private String serverDataFileName;


    public ShutdownHook(Logger logger, Data data) {
		this.logger = logger;
        this.data = data;
        this.serverDataFileName = server.ServerLauncher.FILENAME;
	}

	@Override
	public void run()
    {
        logger.info("Server Shutdown");
        save();
    }

    void save() {
        try (ObjectOutputStream encode = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(serverDataFileName)))) {
            encode.writeObject(data);
        } catch (IOException ex) {
            System.err.println("write error: " + ex);
        }
    }


}
