package server.interfaces;

public interface Quiz {

	public abstract String getQuizName();

	public abstract int getQuizID();

	public abstract boolean isActive();

	public abstract void setActive(boolean active);

}