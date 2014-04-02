package server.Factories;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

/**
 * Created by Michael Bragg
 * Factory tp create objects related to files and streams.
 */
public class FileFactory {

    private static FileFactory fileFactory;

    private FileFactory() {
        // Private empty constructor
    }

    public static FileFactory getInstance() {
        if (fileFactory == null) {
            fileFactory = new FileFactory();
        }
        return fileFactory;
    }

    public ObjectOutputStream getObjectOutputStream(String fileName) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    public ObjectInputStream getObjectInputStream(String fileName) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
    }

    public FileInputStream getFileInputStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(fileName);
    }

    public Handler getFileHandler(String fileName, boolean append) throws IOException {
        return new FileHandler(fileName, append);
    }
}