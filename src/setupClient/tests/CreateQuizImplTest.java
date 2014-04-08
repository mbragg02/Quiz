import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import server.interfaces.Question;
import server.interfaces.Server;
import setupClient.controllers.CreateQuizImpl;
import setupClient.views.CreateQuizView;

import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Michael Bragg
 */
public class CreateQuizImplTest {

    private static final String QUIZ_NAME = "Test Quiz";
    private static final String QUESTION = "Question";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CreateQuizImpl client;
    @Mock
    private Server model;
    @Mock
    private CreateQuizView view;
    @Mock
    private List<Question> nonEmptyQuizList;
    @Mock
    private Question question;
    @Mock
    private List<String> nonEmptyAnswerList;

    @BeforeClass
    public static void beforeClass() {


    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        nonEmptyQuizList.add(question);
        nonEmptyQuizList.add(question);
        nonEmptyAnswerList.add(anyString());
        nonEmptyAnswerList.add(anyString());

        when(view.getNextIntFromConsole()).thenReturn(1);
        when(view.getNextLineFromConsole()).thenReturn(QUIZ_NAME);
        when(model.createQuiz(anyString())).thenReturn(0);

        this.client = new CreateQuizImpl(model, view);
    }

    @Test
    public void testLaunch() throws Exception {

    }

    @Test
    public void testCreateQuizWithName() throws Exception {
        client.createQuizWithName();
        verify(view).printNameInputRequest();
        verify(model).createQuiz(QUIZ_NAME);
    }

//    @Test
//    public void testCreateQuizWithNameException() throws Exception  {
//       // thrown.expect(IllegalArgumentException.class);
//        when(view.getNextLineFromConsole()).thenReturn(EMPTY_STRING);
//        client.createQuizWithName();
//        verify(view).printNameInputRequest();
//        verify(model).createQuiz(EMPTY_STRING);
//        verify(view).printException(anyString());
//    }

    @Test
    public void testAddQuestionsAndAnswers() throws Exception {
        when(view.getNextLineFromConsole()).thenReturn(QUESTION, "y", "y");
        when(model.getQuizQuestionsAndAnswers(0)).thenReturn(nonEmptyQuizList);
        when(model.addQuestionToQuiz(anyInt(), anyString())).thenReturn(1);
        when(model.getAnswersForQuestion(anyInt(), anyInt())).thenReturn(nonEmptyAnswerList);

        client.addQuestionsAndAnswers();

        verify(view).printQuestionInputRequest();
        verify(view).getNextLineFromConsole();
        verify(model).addQuestionToQuiz(0, QUESTION);
        verify(view).printException(anyString());

    }

    @Test
    public void testSelectCorrectAnswer() throws Exception {
        client.selectCorrectAnswer(5);
        verify(view).printCorrectAnswerRequest();
        verify(view).getNextIntFromConsole();
        verify(model).setCorrectAnswer(anyInt(), eq(5), eq(0));
        verify(view).getNextLineFromConsole();
    }

    @Test
    public void testDisplayQuestionsAndAnswers() throws Exception {
        client.displayQuestionsAndAnswers(anyInt());
    }

    @Test
    public void testSetQuizStatus() throws Exception {
        client.setQuizStatus();

    }
}
