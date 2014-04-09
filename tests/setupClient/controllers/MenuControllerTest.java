package setupClient.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import setupClient.views.MenuView;

import java.rmi.RemoteException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Michael Bragg
 *         Tests for the setup clients menu controller
 */
public class MenuControllerTest {

    private MenuController client;

    @Mock
    private CreateControllerImpl createController;
    @Mock
    private EndController endController;
    @Mock
    private DisplayController displayController;
    @Mock
    private MenuView view;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.client = new MenuController(createController, endController, displayController, view);
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
    public void testLaunchThenCallViewActiveQuizzes() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("2").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(displayController).launch();
        verify(view).printExitMessage();

    }

    /*
    Test for launching the menu and ending a running quiz game.
    */
    @Test
    public void testLaunchThenCallEndQuiz() throws RemoteException {
        when(view.getNextLineFromConsole()).thenReturn("3").thenReturn("exit");

        client.launch();

        verify(view).printWelcomeMessage();
        verify(view, times(2)).printMainMenu();
        verify(view, times(2)).getNextLineFromConsole();
        verify(endController).launch();
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
