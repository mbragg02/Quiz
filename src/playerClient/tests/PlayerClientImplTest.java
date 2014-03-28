package playerClient.tests;

import org.junit.Before;
import org.junit.Test;
import playerClient.controllers.PlayerClientImpl;
import playerClient.interfaces.PlayerClient;
import playerClient.views.PlayerClientView;
import server.interfaces.Server;
import server.models.ServerImpl;

import java.rmi.RemoteException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerClientImplTest {

    private Server model;
    private PlayerClient client;
    private PlayerClientView view;

    @Before
    public void setUp() throws Exception {
        this.model = mock(ServerImpl.class);

        this.view = mock(PlayerClientView.class);
        when(view.getNextIntFromConsole()).thenReturn(1);
        when(view.getNextLineFromConsole()).thenReturn("Michael");
        this.client = new PlayerClientImpl(model, view);

    }

    @Test
    public void testLaunch() throws RemoteException {
        client.launch();
    }

    @Test
    public void testDisplayActiveQuizzes() throws RemoteException {
        client.displayActiveQuizzes();
    }

    @Test
    public void testSetPlayerQuiz() throws RemoteException {
//		client.setPlayerQuiz();
    }

    @Test
    public void testPlayQuiz() throws RemoteException {
        client.playQuiz();
    }

    @Test
    public void testSubmitScore() throws RemoteException {
        client.submitScore();
    }

}
