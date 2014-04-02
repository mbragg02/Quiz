package server.utilities;

import server.Factories.FileFactory;
import server.Factories.ServerFactory;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Michael Bragg
 * A wrapper class for Logger
 */
public class LoggerWrapper {

    private static final String LOG_FILENAME = "server.log";
    private static Logger logger;

    /*
    Private constructor so only a method inside this class can create new wrappers.
    Creates a Logger and calls prepare to initilize it.
     */
    private LoggerWrapper() {
        logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.FINE);
        prepareLogger();
    }

    /*
    Only creates a new LoggerWrapper if a instance does not already exist.
     */
    private static Logger getLogger() {
        if (logger == null) {
            new LoggerWrapper();
        }
        return logger;
    }

    /**
     * Make a log call with a log level and a message.
     *
     * @param level Level. The level at which the log call is written.
     * @param msg   Stirng. The message to accompany the log entry.
     */
    public static void log(Level level, String msg) {
        getLogger().log(level, msg);
    }

    /*
    Initalizes the logger and sets up writiing to a log file and the console (at diffrent Levels),
    so console only recieves basic informaion & any exceptions.
     */
    private static void prepareLogger() {
        LogManager.getLogManager().reset();
//      logger.setUseParentHandlers(false);

        ServerFactory serverFactory = ServerFactory.getInstance();
        FileFactory fileFactory = FileFactory.getInstance();

        // Handler for writing log messages to a file.
        Handler fileHandler = null;
        try {
            fileHandler = fileFactory.getFileHandler(LOG_FILENAME, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileHandler != null) {
            fileHandler.setFormatter(serverFactory.getSimpleFormatter());
            fileHandler.setLevel(Level.FINER);
        }

        logger.addHandler(fileHandler);

        // Handler for writing log messages to the console. i.e Exceptions
        Handler consoleHandler = serverFactory.getConsoleHandler();
        consoleHandler.setFormatter(serverFactory.getSimpleFormatter());
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);

    }

}