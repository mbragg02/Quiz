package server.utilities;

import java.util.logging.Logger;

/**
 * Shutdown class runs when the server is terminated:
 * Adds a message to the server log file.
 * @author Michael Bragg
 */
public class ShutdownHook extends Thread {
	private final Logger logger;
	
	public ShutdownHook(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run()
    {
        logger.info("Server Shutdown");
    }
}
