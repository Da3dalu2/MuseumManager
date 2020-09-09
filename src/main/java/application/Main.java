package application;

import controller.utils.DbUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import view.ViewImpl;

/**
 * This class represent the Main class of MuseumManager.
 */
public final class Main extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		final View view = new ViewImpl(stage);
		view.launch();
		DbUtils.getDbConnection();

		/*
		 * stage.setOnCloseRequest(e -> { controller.disconnectDatabase();
		 * System.exit(0); });
		 */
	}

	/**
	 * @param args unused
	 */
	public static void main(final String[] args) {
		launch();
	}

}
