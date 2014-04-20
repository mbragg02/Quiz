package controllers;

import interfaces.Server;

import java.rmi.RemoteException;

/**
 * Abstract class for the set-up client controllers
 *
 * @author Michael Bragg
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
