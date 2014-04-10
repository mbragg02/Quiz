package setupClient.views;

/**
 * View for start quiz controller
 *
 * @author Michael Bragg
 */
public class StartQuizView extends SetupClientView {

    public void printQuizIDActivationRequest() {
        System.out.print("Please enter a Quiz ID to activate: ");
    }

    public void printActivationSuccess() {
        System.out.println(" Activation succesfull. The Quiz is now avaiable to play.");}

}
