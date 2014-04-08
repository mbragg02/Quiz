
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import playerClient.controllers.PlayerClientImpl;
import playerClient.interfaces.PlayerClient;
import playerClient.views.PlayerClientView;
import server.interfaces.Server;

import java.rmi.RemoteException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayerClientImplTest {


    private PlayerClient client;

    @Mock
    private Server model;

    @Mock
    private PlayerClientView view;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(view.getNextIntFromConsole()).thenReturn(1);
        when(view.getNextLineFromConsole()).thenReturn("Michael");
        this.client = new PlayerClientImpl(model, view);
    }

    @Test
    public void testLaunch() throws RemoteException {
        client.launch();
        verify(view).displayWelcomeMessage();
    }

    @Test
    public void testDisplayActiveQuizzes() throws RemoteException {
        client.displayActiveQuizzes();
        verify(view).displayCurrentActiveQuizesMessage();
    }

    @Test
    public void testPlayQuiz() throws RemoteException {
        client.playQuiz();
        verify(model).startGame(anyInt(), anyString());
    }

    @Test
    public void testSubmitScore() throws RemoteException {
        client.submitScore();
        verify(model).submitScore(anyInt(), anyInt(), anyInt());
        verify(view).displayScoreDetails(anyInt(), anyInt());
    }

}
