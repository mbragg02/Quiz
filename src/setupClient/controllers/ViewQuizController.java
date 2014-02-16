package setupClient.controllers;

import java.util.List;

import server.interfaces.Quiz;
import server.interfaces.Server;

public class ViewQuizController extends Controller {
	
	
	public ViewQuizController(Server server) {
		super(server);
	}
	
	@Override
	public void launch() {
		getActiveQuizzes();
		
	}
	
	public void getActiveQuizzes() {
		List<Quiz> quizzes = server.getActiveQuizes();
		if(quizzes.isEmpty()) {
			System.out.println("Currently no active quizzes");
		} else {
			int quizCounter = 1;
			for (Quiz quiz : quizzes) {
				System.out.println(quizCounter + ": " + quiz.getQuizName() + " [" + quiz.getQuizID() + "]");
			}
		}
	}
}
