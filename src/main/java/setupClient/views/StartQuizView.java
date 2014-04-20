package setupClient.views;

import static server.utilities.MessageProperties.msg;

/**
 * View for start quiz controller
 *
 * @author Michael Bragg
 */
public class StartQuizView extends SetupClientView {

    public void printQuizIDActivationRequest() {
        System.out.print(msg("quizid_activation_prompt") + " ");
    }

    public void printActivationSuccess() {
        System.out.println(msg("quiz_activation_success"));
    }

}
