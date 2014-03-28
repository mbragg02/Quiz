package setupClient.controllers;

import server.interfaces.Server;

import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 *         Abstract class for a set-up client action controller
 */
public abstract class Controller {

    protected Server model;

    public Controller() {
    }

    public Controller(Server model) {
        this.model = model;
    }

    /**
     * Launch method to run a controller action.
     *
     * @throws RemoteException
     */
    public abstract void launch() throws RemoteException;

}
