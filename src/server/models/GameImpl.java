package server.models;

import java.io.Serializable;

import server.interfaces.Game;

public class GameImpl implements Game, Serializable {

	private static final long serialVersionUID = -3849918143166439866L;
	private int gameID;
	private String playerName;
	private int score;
	private int numberOfQuestions;
	private String dateCompleted;
	
	public GameImpl(int gameID, String playerName) {
		setGameID(gameID);
		setPlayerName(playerName);
	}


	@Override
	public int getGameID() {
		return gameID;
	}


	private void setGameID(int gameID) {
		this.gameID = gameID;
	}


	@Override
	public String getPlayerName() {
		return playerName;
	}


	private void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	@Override
	public int getScore() {
		return score;
	}


	@Override
	public void setPlayerScoreWithDate(int score, String date) {
		this.score = score;
		this.dateCompleted = date;
	}
	
	@Override
	public String getDateCompleted() {
		return this.dateCompleted;
	}

	@Override
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	@Override
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

}
