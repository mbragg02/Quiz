package playerClient;

import java.rmi.RemoteException;

public interface PlayerClientController {
	
	void launch() throws RemoteException;
	
	void displayActiveQuizzes() throws RemoteException;

	void playQuiz() throws RemoteException;

	void setPlayerQuiz() throws RemoteException;

	void submitScore() throws RemoteException;

}