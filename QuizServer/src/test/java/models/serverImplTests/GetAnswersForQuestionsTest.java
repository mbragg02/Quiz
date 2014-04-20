package models.serverImplTests;

import factories.QuizFactory;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;
import models.ServerData;
import models.ServerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
