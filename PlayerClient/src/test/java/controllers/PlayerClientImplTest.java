package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import interfaces.PlayerClient;
import views.PlayerClientView;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayerClientImplTest {


    public static final String MOCK_ANSWER = "Mock Answer";
    public static final String MOCK_QUESTION = "Mock Question";
    private PlayerClient client;

    @Mock
    private Server model;
    @Mock
    private PlayerClientView view;
    @Mock
    private Quiz quiz;
    @Mock
    private Question question;
    @Mock
    private List<Quiz> emptyQuizList;
    @Mock
    private List<Quiz> nonEmptyQuizList;
    @Mock
    private List<Question> nonEmptyQuestionList;
    @Mock
    private List<String> nonEmptyAnswerList;
    @Mock
    private Iterator<Quiz> quizIterator;
    @Mock
    private Iterator<Question> questionIterator;
    @Mock
    private Iterator<String> answerIterator;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(view.getNextIntFromConsole()).thenReturn(1);
        when(view.getNextLineFromConsole()).thenReturn("Michael");
        when(emptyQuizList.isEmpty()).thenReturn(true);

        nonEmptyQuizList.add(0, quiz);
        nonEmptyQuestionList.add(0, question);
        nonEmptyAnswerList.add(0, MOCK_ANSWER);

        this.client = new PlayerClientImpl(model, view);
    }


    /*
    Tests launching the playerclient where there are no current active quizzes on the server.
     */
    @Test
    public void testLaunchNoActiveQuizzes() throws RemoteException {
        when(model.getActiveQuizzes()).thenReturn(emptyQuizList);

        client.launch();

        verify(view).displayWelcomeMessage();
        verify(view).displayNoActiveQuizzesMessage();
    }

    @Test
    public void testSetPlayerName() {
        when(view.getNextLineFromConsole()).thenReturn("Test name");

        client.setPlayerName();

        verify(view).displayNameRequest();
        verify(view).getNextLineFromConsole();
    }

    @Test
    public void testSetPlayerNameException() {
        doThrow(new NoSuchElementException()).when(view).getNextLineFromConsole();

        client.setPlayerName();

        verify(view).displayNameRequest();
        verify(view).displayException(anyString());
    }


    /*
    Tests displaying quizzes from the server
     */
    @Test
    public void testDisplayActiveQuizzes() throws RemoteException {
        when(model.getActiveQuizzes()).thenReturn(nonEmptyQuizList);
        when(nonEmptyQuizList.iterator()).thenReturn(quizIterator);

        when(quiz.getQuizID()).thenReturn(50);
        when(quiz.getQuizName()).thenReturn("Test Quiz");

        // Mocked iterators behaviour
        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(quizIterator.next()).thenReturn(quiz);


        client.displayActiveQuizzes();

        verify(view).displayCurrentActiveQuizzesMessage();
        verify(view).displayQuizDetails(50, "Test Quiz");
    }


    /*
    Default call to server to start a new game
     */
    @Test
    public void testPlayQuiz() throws RemoteException {
        client.playGame();

        verify(model).startGame(anyInt(), anyString());
    }

    /*
    Tests PlayQuiz where the player name has not been set. Catches a IllegalArgumentException
     */
    @Test
    public void testPlayQuizIllegalArgumentException() throws RemoteException {
        doThrow(new IllegalArgumentException()).doReturn(0).when(model).startGame(anyInt(), anyString());

        client.playGame();

        verify(model, times(2)).startGame(anyInt(), anyString());
        verify(view).displayException(anyString());
    }

    /*
    Tests PlayQuiz where there is a invlaid or inactive quiz ID. Catches a NullPointerException
     */
    @Test
    public void testPlayQuizNullPointerException() throws RemoteException {
        doThrow(new NullPointerException()).doReturn(0).when(model).startGame(anyInt(), anyString());

        client.playGame();

        verify(model, times(2)).startGame(anyInt(), anyString());
        verify(view).displayException(anyString());
    }

    // Default behaviour of displaying a question with it's answers
    @Test
    public void testDisplayQuestionAndAnswers() throws RemoteException {
        // Set up mock question getters return values
        when(question.getQuestion()).thenReturn(MOCK_QUESTION);
        when(question.getCorrectAnswerID()).thenReturn(0);

        // Set up non-empty Question List and iterator behaviour
        when(model.getQuizQuestionsAndAnswers(anyInt())).thenReturn(nonEmptyQuestionList);
        when(nonEmptyQuestionList.iterator()).thenReturn(questionIterator);
        when(questionIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(questionIterator.next()).thenReturn(question);

        // Set up non-empty Answer List and iterator behaviour
        when(question.getAnswers()).thenReturn(nonEmptyAnswerList);
        when(nonEmptyAnswerList.iterator()).thenReturn(answerIterator);
        when(answerIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(answerIterator.next()).thenReturn(MOCK_ANSWER);

        client.displayQuestions();

        // Verify correct view methods were called
        verify(model).getQuizQuestionsAndAnswers(anyInt());
        verify(view).displayQuestion(1, MOCK_QUESTION);
        verify(view).displayAnswer(1, MOCK_ANSWER);
    }

    /*
    Tests NullpointerException is caught and view method called
     */
    @Test
    public void testDisplayQuestionException() throws RemoteException {
        doThrow(new NullPointerException()).when(model).getQuizQuestionsAndAnswers(anyInt());

        client.displayQuestions();

        verify(view).displayException(anyString());
    }

    /*
    Test asking/getting an input from the user.
     */
    @Test
    public void testSelectAnswer() {
        client.selectAnswer();

        verify(view).displayAnswerRequest();
        verify(view).getNextIntFromConsole();
    }

    /*
    Tests call to server to submit score & view messages
     */
    @Test
    public void testSubmitScore() throws RemoteException {
        client.submitScore();
        verify(model).submitScore(anyInt(), anyInt(), anyInt());
        verify(view).displayScoreDetails(anyInt(), anyInt());
        verify(view).displayExitMessage(anyString());
    }

    /*
    Tests that NullPointerExceptions are caught.
     */
    @Test
    public void testSubmitScoreCatchExceptions() throws RemoteException {
        doThrow(new NullPointerException())
                .doNothing()
                .when(model)
                .submitScore(anyInt(), anyInt(), anyInt());

        client.submitScore();

        verify(model).submitScore(anyInt(), anyInt(), anyInt());
        verify(view).displayException(anyString());
    }

}
