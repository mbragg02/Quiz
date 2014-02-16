package playerClient;

import java.rmi.RemoteException;

public interface PlayerClient {
	
	void launch() throws RemoteException;
	
	void displayActiveQuizzes() throws RemoteException;

	void playQuiz() throws RemoteException;

	void setPlayerQuiz() throws RemoteException;

	void submitScore() throws RemoteException;

}