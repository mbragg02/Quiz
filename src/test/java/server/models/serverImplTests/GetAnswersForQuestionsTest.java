package server.models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.factories.QuizFactory;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;
import server.models.ServerData;
import server.models.ServerImpl;

import java.rmi.RemoteException;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the getAnswersForQuestions method in serverImpl
 *
 * @author Michael Bragg
 */
public class GetAnswersForQuestionsTest {
    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private QuizFactory factory;
    @Mock
    private Quiz quiz;
    @Mock
    private Question question;
    @Mock
    private List<String> answers;


    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }


    // getAnswersForQuestion
    @Test
    public void testGetAnswersForQuestion() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);

        server.getAnswersForQuestion(10, 20);

        verify(question).getAnswers();
    }
}
