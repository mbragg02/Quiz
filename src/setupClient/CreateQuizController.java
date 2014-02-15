package setupClient;

import java.util.Scanner;

import server.Server;

public class CreateQuizController implements Controller {
	
	private Server server;
	private Scanner in;
	private int quizId;	
	
	public CreateQuizController(Server server) {
		this.server = server;
		in = new Scanner(System.in);
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}


}
