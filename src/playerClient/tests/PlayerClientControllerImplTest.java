package playerClient.tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import playerClient.PlayerClientController;
import playerClient.PlayerClientControllerImpl;
import playerClient.PlayerClientView;
import server.interfaces.Server;
import server.models.ServerImpl;
import static org.mockito.Mockito.*;

public class PlayerClientControllerImplTest {

	private Server model;
	private PlayerClientController client;
	private PlayerClientView view;
	@Before
	public void setUp() throws Exception {
		this.model = mock(ServerImpl.class);
		
		this.view = mock(PlayerClientView.class);
		when(view.getNextIntFromConsole()).thenReturn(1);
		when(view.getNextLineFromConsole()).thenReturn("Michael");
		this.client = new PlayerClientControllerImpl(model, view);
		
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
