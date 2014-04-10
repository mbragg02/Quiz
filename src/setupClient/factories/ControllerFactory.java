package setupClient.factories;

import server.interfaces.Server;
import setupClient.controllers.*;
import setupClient.views.CreateQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;
import setupClient.views.StartQuizView;

/**
 * Factory for the controller actions for the set-up client
 *
 * @author Michael Bragg
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
        return new CreateQuizControllerImpl(model, view);
    }

    Controller getEndQuizController(EndQuizView view) {
        return new EndQuizController(model, view);
    }

    Controller getStartQuizController(StartQuizView view) {
        return new StartQuizController(model, view);
    }

    public Controller getMenuController(MenuView menuView, CreateQuizView Createview, EndQuizView endView, StartQuizView view) {
        return new MenuController(getCreateQuizController(Createview), getEndQuizController(endView), getStartQuizController(view), menuView);
    }


}