package server;

public class Game {
	private int gameID;
	private String playerName;
	private int score;
	
	public Game(int gameID, String playerName) {
		setGameID(gameID);
		setPlayerName(playerName);
		setScore(0);
	}


	public int getGameID() {
		return gameID;
	}


	private void setGameID(int gameID) {
		this.gameID = gameID;
	}


	public String getPlayerName() {
		return playerName;
	}


	private void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}

}
