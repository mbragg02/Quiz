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
 * Test for the setCorrectAnswer method in serverImpl
 *
 * @author Michael Bragg
 */
public class SetCorrectAnswerTest {

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

    // setCorrectAnswer
    @Test
    public void testSetCorrectAnswer() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(31);

        server.setCorrectAnswer(10, 20, 30);

        verify(question).setCorrectAnswerID(30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCorrectAnswerTooLarge() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(30);

        server.setCorrectAnswer(10, 20, 30);

        verify(question).setCorrectAnswerID(30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCorrectAnswerTooSmall() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(30);

        server.setCorrectAnswer(10, 20, -1);

        verify(question).setCorrectAnswerID(30);
    }
}
