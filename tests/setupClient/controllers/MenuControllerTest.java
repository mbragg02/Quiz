package setupClient.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import setupClient.views.MenuView;

import java.rmi.RemoteException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for the setup clients menu controller
 *
 * @author Michael Bragg
 */
public class MenuControllerTest {

    private MenuController client;

    @Mock
    private CreateQuizControllerImpl createController;
    @Mock
    private EndQuizController endQuizController;
    @Mock
    private StartQuizController startQuizController;
    @Mock
    private MenuView view;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.client = new MenuController(createController, endQuizController, startQuizController, view);
    }

    /*
    Testing launching the menu then immediately exiting the application
     */
    @Test
    public void testLaunchThenExit() throws Exception {
        when(view.getNextLineFromConsole()).thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view).printMainMenu();
        verify(view).getNextLineFromConsole();
        verify(view).printExitMessage();
    }

    /*
    Test for invalid user input. Catches Integer.parseInt NumberFormatException
     */
    @Test
    public void testLaunchInvalidInput() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("1111111111111111111111111111111").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(view).printInvalidInputMessage();
        verify(view).printExitMessage();
    }

    /*
    Test for launching the menu and seleting create a new Quiz
     */
    @Test
    public void testLaunchThenCallCreateQuiz() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("1").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(createController).launch();
        verify(view).printExitMessage();
    }

    /*
    Test for launching the menu and displaying all active quizzes
     */
    @Test
    public void testLaunchThenCallStartQuizController() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("2").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(startQuizController).launch();
        verify(view).printExitMessage();

    }

    /*
    Test for launching the menu and ending a running quiz game.
    */
    @Test
    public void testLaunchThenCallEndQuizController() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("3").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(endQuizController).launch();
        verify(view).printExitMessage();

    }

    /*
    Test for calling a view message when the menu input is not in range i.e. valid
     */
    @Test
    public void testLaunchOutOfRangeCall() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("4").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(view).printInvalidInputMessage();
        verify(view).printExitMessage();
    }

}
