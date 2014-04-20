package controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import interfaces.Question;
import interfaces.Server;
import views.CreateQuizView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Michael Bragg
 */
public class CreateQuizControllerImplTest {

    private static final String QUIZ_NAME = "Test Quiz";
    private static final String QUESTION = "Question";
    private static final String ANSWER = "Answer";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CreateQuizControllerImpl client;
    @Mock
    private Server model;
    @Mock
    private CreateQuizView view;
    @Mock
    private List<Question> nonEmptyQuestionList;
    @Mock
    private Question question;
    @Mock
    private List<String> nonEmptyAnswerList;
    @Mock
    private List<String> emptyAnswerList;
    @Mock
    private List<Question> emptyQuestionList;
    @Mock
    private Iterator<Question> mockQuestionIterator;
    @Mock
    private Iterator<String> mockAnswerIterator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        nonEmptyQuestionList.add(question);
        nonEmptyQuestionList.add(question);
        nonEmptyAnswerList.add(anyString());
        nonEmptyAnswerList.add(anyString());

        when(emptyAnswerList.isEmpty()).thenReturn(true);
        when(emptyQuestionList.isEmpty()).thenReturn(true);
        when(view.getNextIntFromConsole()).thenReturn(1);
        when(view.getNextLineFromConsole()).thenReturn(QUIZ_NAME);
        when(model.createQuiz(anyString())).thenReturn(0);

        this.client = new CreateQuizControllerImpl(model, view);
    }

    @Test
    public void testCreateQuizWithName() throws Exception {
        client.createQuizWithName();

        verify(view).printNameInputRequest();
        verify(model).createQuiz(QUIZ_NAME);
    }

    /*
    Tests adding a question then finishing the loop
     */
    @Test
    public void testAddQuestion() throws RemoteException {
        when(view.getNextLineFromConsole())
                .thenReturn(QUESTION, "y");

        client.addQuestion();

        verify(view).printQuestionInputRequest();
        verify(view).getNextLineFromConsole();
        verify(model).addQuestionToQuiz(0, QUESTION);
    }

    /*
    Test trying to finish the question loop without adding a question first
     */
    @Test
    public void testAddZeroQuestion() throws RemoteException {
        when(view.getNextLineFromConsole())
                .thenReturn("y", "y");
        when(model.getQuizQuestionsAndAnswers(anyInt()))
                .thenReturn(emptyQuestionList)
                .thenReturn(nonEmptyQuestionList);

        client.addQuestion();

        verify(view, times(2)).printQuestionInputRequest();
        verify(view, times(2)).getNextLineFromConsole();
        verify(view).printQuestionNumberException();
    }

    /*
    Tests adding one answer then inputting "y" to finish
     */
    @Test
    public void testAddOneAnswer() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn(ANSWER, "y");
        when(model.getAnswersForQuestion(anyInt(), anyInt()))
                .thenReturn(nonEmptyAnswerList);

        client.addAnswers(1);

        verify(view, times(2)).printAnswerInputRequest();
        verify(view, times(2)).getNextLineFromConsole();
        verify(model).addAnswerToQuestion(anyInt(), eq(1), eq(ANSWER));
    }

    /*
    Tests trying to add zero answers
     */
    @Test
    public void testAddZeroAnswers() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("y", "y");
        when(model.getAnswersForQuestion(anyInt(), anyInt()))
                .thenReturn(emptyAnswerList)
                .thenReturn(nonEmptyAnswerList);

        client.addAnswers(1);

        verify(view, times(2)).printAnswerInputRequest();
        verify(view, times(2)).getNextLineFromConsole();
        verify(view).printAnswerNumberException();
    }

    /*
    Default flow for selecting the correct answer
     */
    @Test
    public void testSelectCorrectAnswer() throws Exception {
        client.selectCorrectAnswer(5);

        verify(view).printCorrectAnswerRequest();
        verify(view).getNextIntFromConsole();
        verify(model).setCorrectAnswer(anyInt(), eq(5), eq(0));
        verify(view).getNextLineFromConsole();
    }

    /*
    Tests catching InputMismatchException exception
     */
    @Test
    public void testSelectCorrectAnswerInputMismatchException() throws RemoteException {
        doThrow(new InputMismatchException())
                .doNothing()
                .when(model).setCorrectAnswer(anyInt(), anyInt(), anyInt());

        client.selectCorrectAnswer(99);

        verify(view, times(2)).printCorrectAnswerRequest();
        verify(view, times(2)).getNextIntFromConsole();
        verify(view).printInvalidInputException();
    }

    /*
    Tests catching IllegalArgumentException exception
     */
    @Test
    public void testSelectCorrectAnswerIllegalArgumentException() throws RemoteException {
        doThrow(new IllegalArgumentException()).doNothing()
                .when(model).setCorrectAnswer(anyInt(), anyInt(), anyInt());

        client.selectCorrectAnswer(99);

        verify(view, times(2)).printCorrectAnswerRequest();
        verify(view, times(2)).getNextIntFromConsole();
        verify(view).printException(anyString());
    }

    @Test
    public void testDisplayQuestionsAndAnswers() throws Exception {
        // Mocked iterators behaviour
        when(mockQuestionIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(mockAnswerIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(mockQuestionIterator.next()).thenReturn(question);
        when(mockAnswerIterator.next()).thenReturn(ANSWER);

        // Mocked question getters return values
        when(question.getQuestionID()).thenReturn(10);
        when(question.getAnswers()).thenReturn(nonEmptyAnswerList);
        when(model.getQuizQuestionsAndAnswers(anyInt())).thenReturn(nonEmptyQuestionList);

        // Assign mocked iterators to be returned
        when(nonEmptyQuestionList.iterator()).thenReturn(mockQuestionIterator);
        when(nonEmptyAnswerList.iterator()).thenReturn(mockAnswerIterator);

        client.displayQuestionsAndAnswers(10);

        verify(view).printQuestion(anyString());
        verify(view).printAnswer(anyInt(), eq(ANSWER));
    }

    /*
    Test for setting a quiz to inactive (Just calling the view, quizzes are inactive by default)
     */
    @Test
    public void testSetQuizStatusInactive() throws Exception {
        client.setQuizStatus();

        verify(view).printActivationRequest();
        verify(view).getNextLineFromConsole();
        verify(view).printInActiveMessage();
    }

    /*
    Test for setting a quiz to active
     */
    @Test
    public void testSetQuizStatusActive() throws Exception {
        when(view.getNextLineFromConsole()).thenReturn("y");

        client.setQuizStatus();

        verify(view).printActivationRequest();
        verify(view).getNextLineFromConsole();
        verify(model).setQuizActive(anyInt());
        verify(view).printActiveMessage();
    }
}
