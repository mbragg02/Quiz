package setupClient.factories;

import setupClient.views.CreateQuizView;
import setupClient.views.DisplayQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;

/**
 * Factory for creating different views the diffrent actions of the setup client
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

    public DisplayQuizView getDisplayQuizView() {
        return new DisplayQuizView();
    }
}
