package server.interfaces;

public interface Game {

	public abstract int getGameID();

	public abstract String getPlayerName();

	public abstract int getScore();

	public abstract void setScore(int score);

}