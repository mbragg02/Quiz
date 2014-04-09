package setupClient.factories;

import server.interfaces.Server;
import setupClient.controllers.*;
import setupClient.views.CreateQuizView;
import setupClient.views.DisplayQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;

/**
 * @author Michael Bragg
 *         Factory for the controller actions for the set-up client
 */
public class ControllerFactory {

    private static ControllerFactory controllerFactory;
    private Server model;

    private ControllerFactory() {
        // Private empty constructor for factory
    }

    public static ControllerFactory getInstance() {
        if (controllerFactory == null) {
            controllerFactory = new ControllerFactory();
        }
        return controllerFactory;
    }

    public void setServer(Server model) {
        this.model = model;
    }

    Controller getCreateQuizController(CreateQuizView view) {
        return new CreateControllerImpl(model, view);
    }

    Controller getEndQuizController(EndQuizView view) {
        return new EndController(model, view);
    }

    Controller getViewQuizController(DisplayQuizView view) {
        return new DisplayController(model, view);
    }

    public Controller getMenuController(MenuView menuView, CreateQuizView Createview, EndQuizView endView, DisplayQuizView view) {
        return new MenuController(getCreateQuizController(Createview), getEndQuizController(endView), getViewQuizController(view), menuView);
    }


}