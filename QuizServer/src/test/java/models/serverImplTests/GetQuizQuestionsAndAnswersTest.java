package models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import factories.QuizFactory;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;
import models.ServerData;
import models.ServerImpl;

import java.rmi.RemoteException;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the getQuizQuestionsAndAnswers method in serverImpl
 *
 * @author Michael Bragg
 */
public class GetQuizQuestionsAndAnswersTest {

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
    private List<Question> questions;


    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // getQuizQuestionsAndAnswers
    @Test
    public void testGetQuizQuestionsAndAnswers() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestions()).thenReturn(questions);

        server.getQuizQuestionsAndAnswers(10);

        verify(quiz).getQuestions();
    }
}
