package server.interfaces;

public interface Game {

	int getGameID();

	String getPlayerName();

	int getScore();

	void setPlayerScoreWithDate(int score, String date);

	String getDateCompleted();

	int getNumberOfQuestions();

	void setNumberOfQuestions(int numberOfQuestions);

}