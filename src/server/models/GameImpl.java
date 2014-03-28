package server.models;

import server.interfaces.Game;

import java.io.Serializable;

/**
 * @author Michael Bragg
 * Class for a game played by a "player client". A game is initialized with a player name and unique ID.
 */
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
		this.score = 0;
	}

	@Override
	public int getGameID() {
		return gameID;
	}

	@Override
	public String getPlayerName() {
		return playerName;
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

    private void setGameID(int gameID) {
        this.gameID = gameID;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
