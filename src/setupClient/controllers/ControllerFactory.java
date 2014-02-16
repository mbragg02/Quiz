package setupClient.controllers;

import server.interfaces.Server;
import setupClient.views.CreateQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;
import setupClient.views.ViewQuizView;

public class ControllerFactory {
	
	private static ControllerFactory controllerFactory;
	private Server model;
	
	private ControllerFactory(){}
	
	public static ControllerFactory getInstance() {
		if (controllerFactory == null) {
			controllerFactory = new ControllerFactory();
		}
		return controllerFactory;
	}
	
	public void setServer(Server model) {
		this.model = model;
	}
	
	public Controller getCreateQuizController(CreateQuizView view) {
		return new CreateQuizController(model, view);
	}
	public Controller getEndQuizController(EndQuizView view) {
		return new EndQuizController(model, view);
	}
	public Controller getViewQuizController(ViewQuizView view) {
		return new ViewQuizController(model, view);
	}
	public Controller getMenuController(MenuView menuView, CreateQuizView Createview, EndQuizView endView, ViewQuizView view ) {
		return new MenuController(getCreateQuizController(Createview), getEndQuizController(endView), getViewQuizController(view), menuView);
	}
	

}
