package setupClient.controllers;

import server.interfaces.Server;

import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 *         Abstract class for the set-up client controllers
 */
public abstract class Controller {

    protected Server model;

    public Controller() {
        // Intentional empty default constructor
    }

    Controller(Server model) {
        this.model = model;
    }

    /**
     * Launch method to run a controller action.
     *
     * @throws RemoteException
     */
    public abstract void launch() throws RemoteException;

}
