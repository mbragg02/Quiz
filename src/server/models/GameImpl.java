package server.models;

import server.interfaces.Game;

public class GameImpl implements Game {
	private int gameID;
	private String playerName;
	private int score;
	
	public GameImpl(int gameID, String playerName) {
		setGameID(gameID);
		setPlayerName(playerName);
		setScore(0);
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
	public void setScore(int score) {
		this.score = score;
	}

}
