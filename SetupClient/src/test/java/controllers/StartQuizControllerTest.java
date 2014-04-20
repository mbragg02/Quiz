package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import interfaces.Quiz;
import interfaces.Server;
import views.StartQuizView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for the StartQuizController class
 *
 * @author Michael Bragg
 */
public class StartQuizControllerTest {

    private StartQuizController client;

    @Mock
    private Server model;
    @Mock
    private StartQuizView view;
    @Mock
    private List<Quiz> nonEmptyListOfQuizzes;
    @Mock
    private List<Quiz> emptyListOfquizzes;
    @Mock
    private Quiz quiz;
    @Mock
    private Iterator<Quiz> quizIterator;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        // Setup mock empty/non-empty quiz lists
        nonEmptyListOfQuizzes.add(0, quiz);
        when(emptyListOfquizzes.isEmpty()).thenReturn(true);

        // Set up mock Quiz iterator behaviour
        when(nonEmptyListOfQuizzes.iterator()).thenReturn(quizIterator);
        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(quizIterator.next()).thenReturn(quiz);

        when(view.getNextIntFromConsole()).thenReturn(10);

        this.client = new StartQuizController(model, view);
    }

    @Test
    public void testLaunchNoInactiveQuizzes() throws Exception {
        when(model.getInactiveQuizzes()).thenReturn(emptyListOfquizzes);

        client.launch();

        verify(view).printNoInActiveQuizzesMessage();
    }

    @Test
    public void testLaunchInputMismatchException() throws RemoteException {
        when(model.getInactiveQuizzes()).thenReturn(nonEmptyListOfQuizzes);
        doThrow(new InputMismatchException()).doReturn(0).when(view).getNextIntFromConsole();

        client.launch();

        verify(view, times(1)).printQuizDetails(anyInt(), anyString());
        verify(view, times(2)).printQuizIDActivationRequest();
        verify(view, times(2)).getNextLineFromConsole();
        verify(view).printException(anyString());

    }

    @Test
    public void testLaunchNullPointerException() throws RemoteException {
        when(model.getInactiveQuizzes()).thenReturn(nonEmptyListOfQuizzes);
        doThrow(new NullPointerException()).doNothing().when(model).setQuizActive(anyInt());

        client.launch();

        verify(view, times(1)).printQuizDetails(anyInt(), anyString());
        verify(view, times(2)).printQuizIDActivationRequest();
        verify(view, times(2)).getNextLineFromConsole();
        verify(model, times(2)).setQuizActive(anyInt());
        verify(view).printException(anyString());
        verify(view).printActivationSuccess();
    }

}
