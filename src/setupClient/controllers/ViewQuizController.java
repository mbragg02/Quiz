package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.List;

import server.interfaces.Quiz;
import server.interfaces.Server;
import setupClient.views.ViewQuizView;

public class ViewQuizController extends Controller {
	
	private ViewQuizView view;
	
	public ViewQuizController(Server model, ViewQuizView view) {
		super(model);
		this.view = view;
	}
	
	@Override
	public void launch() throws RemoteException {
		List<Quiz> quizzes = model.getActiveQuizes();
		if(quizzes.isEmpty()) {
			System.out.println("Currently no active quizzes");
			view.printNoActiveQuizesMessage();
		} else {
			for (Quiz quiz : quizzes) {
				view.printQuizDetails(quiz.getQuizID(), quiz.getQuizName());
			}
		}
		
	}

}
