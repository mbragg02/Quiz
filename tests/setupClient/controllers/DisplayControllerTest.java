//package setupClient.controllers;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import server.interfaces.Quiz;
//import server.interfaces.Server;
//
//import java.rmi.RemoteException;
//import java.util.Iterator;
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
///**
// * @author Michael Bragg
// *         Tests DisplayQuiz class for the setup client.
// */
//public class DisplayControllerTest {
//
//    private DisplayController client;
//
//    @Mock
//    private Server model;
//    @Mock
//    private DisplayQuizView view;
//    @Mock
//    private List<Quiz> nonEmptyListOfQuizzes;
//    @Mock
//    private List<Quiz> emptyListOfquizzes;
//    @Mock
//    private Quiz quiz;
//    @Mock
//    private Iterator<Quiz> quizIterator;
//
//    @Before
//    public void setUp() throws RemoteException {
//        initMocks(this);
//
//        // Define mock Quiz getters return values
//        when(quiz.getQuizID()).thenReturn(10);
//        when(quiz.getQuizName()).thenReturn("Test Quiz");
//
//        // Setup mock empty/non-empty quiz lists
//        nonEmptyListOfQuizzes.add(0, quiz);
//        when(emptyListOfquizzes.isEmpty()).thenReturn(true);
//
//        // Setup mock quiz iterator behaviour
//        when(nonEmptyListOfQuizzes.iterator()).thenReturn(quizIterator);
//        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
//        when(quizIterator.next()).thenReturn(quiz);
//
//        client = new DisplayController(model, view);
//    }
//
//    /*
//    Test print the quiz details for a non-empty list of quizzes
//     */
//    @Test
//    public void testLaunch() throws Exception {
//        when(model.getActiveQuizzes()).thenReturn(nonEmptyListOfQuizzes);
//
//        client.launch();
//
//        verify(view).printQuizDetails(10, "Test Quiz");
//    }
//
//    /*
//    Test print a message if the list of quizzes is empty
//     */
//    @Test
//    public void testLaunchForEmptyList() throws RemoteException {
//        when(model.getActiveQuizzes()).thenReturn(emptyListOfquizzes);
//
//        client.launch();
//
//        verify(view).printNoActiveQuizesMessage();
//    }
//}
