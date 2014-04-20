package factories;

import controllers.*;
import interfaces.Server;
import views.CreateQuizView;
import views.EndQuizView;
import views.MenuView;
import views.StartQuizView;

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

    public Controller getMenuController(MenuView menuView, CreateQuizView CreateView, EndQuizView endView, StartQuizView view) {
        return new MenuController(getCreateQuizController(CreateView), getEndQuizController(endView), getStartQuizController(view), menuView);
    }


}