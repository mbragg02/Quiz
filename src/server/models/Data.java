package server.models;

import server.interfaces.Game;
import server.interfaces.Quiz;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Michael Bragg
 *
 */
public class Data implements Serializable {

    private ConcurrentMap<Integer, Quiz> quizzes;
    private ConcurrentMap<Integer, List<Game>> games;
    private int quizIDs;
    private int questionIDs;
    private int gameIDs;


    public Data() {
        quizzes = new ConcurrentHashMap<>();
        games = new ConcurrentHashMap<>();
        this.quizIDs = 0;
        this.questionIDs = 0;
        this.gameIDs = 0;
    }

    public ConcurrentMap<Integer, Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(ConcurrentMap<Integer, Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public ConcurrentMap<Integer, List<Game>> getGames() {
        return games;
    }

    public void setGames(ConcurrentMap<Integer, List<Game>> games) {
        this.games = games;
    }

    public void addQuiz(int key, Quiz quiz) {
        quizzes.put(key, quiz);
    }

    public void addGame(int key, List<Game> gamesList) {
        games.put(key, gamesList);

    }

    public Quiz getQuiz(int key) {
        return quizzes.get(key);
    }

    public List<Game> getGame(int key) {
        return games.get(key);
    }

    public int getQuizID() {
        int id = quizIDs;
        ++quizIDs;
        return id;
    }

    public int getQuestionID() {
        int id = questionIDs;
        ++questionIDs;
        return id;
    }

    public int getGameID() {
        int id = gameIDs;
        ++gameIDs;
        return id;
    }

}