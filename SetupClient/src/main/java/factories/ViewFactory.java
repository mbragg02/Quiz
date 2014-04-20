package factories;

import views.CreateQuizView;
import views.EndQuizView;
import views.MenuView;
import views.StartQuizView;

/**
 * Factory for creating different views the different actions of the setup client
 *
 * @author Michael Bragg
 */
public class ViewFactory {

    private static ViewFactory viewFactory;

    private ViewFactory() {
        // Private empty constructor for factory
    }

    public static ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }
        return viewFactory;
    }

    public MenuView getMenuView() {
        return new MenuView();
    }

    public CreateQuizView getCreateQuizView() {
        return new CreateQuizView();
    }

    public EndQuizView getEndQuizView() {
        return new EndQuizView();
    }

    public StartQuizView getStartQuizView() {
        return new StartQuizView();
    }
}
