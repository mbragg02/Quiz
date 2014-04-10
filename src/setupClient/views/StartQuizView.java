package setupClient.views;

import static setupClient.factories.MessageProperties.msg;

/**
 * View for start quiz controller
 *
 * @author Michael Bragg
 */
public class StartQuizView extends SetupClientView {

    public void printQuizIDActivationRequest() {
        System.out.print(msg("quizid_activation_request"));
    }

    public void printActivationSuccess() {
        System.out.println(msg("quiz_activation_success"));
    }

}
