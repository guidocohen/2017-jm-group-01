package ui;

import spark.*;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(3000);
		Router.configure();

		DebugScreen.enableDebugScreen();
	}
}
