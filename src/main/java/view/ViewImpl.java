package view;

import static view.utils.Icons.ICON_32;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.controllers.UIController;

public class ViewImpl implements View {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewImpl.class);
	private static final String TITLE = "Museo archeologico";

	private static Stage stage;
	private UIController mainController;
	private FXMLLoader loader;

	public ViewImpl(final Stage stageToLoad) {
		stage = stageToLoad;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void launch() {
		loadMainScene();
		setMainController();
	}

	private void loadMainScene() {
		final Parent root;
		final Scene scene;

		stage.getIcons().add(new Image(ViewImpl.class.getResourceAsStream(ICON_32.getPath())));

		loader = new FXMLLoader();
		loader.setLocation(ClassLoader.getSystemResource("layouts/root.fxml"));
		try {
			root = loader.load();
			scene = new Scene(root);
			scene.setUserData(loader);
			stage.setScene(scene);
		} catch (final IOException e) {
			LOGGER.info("Fxml file loading failed", e);
		}
		stage.setResizable(false);
		stage.setTitle(TITLE);
		stage.show();
	}

	private void setMainController() {
		mainController = (UIController) loader.getController();
		mainController.initUIController(this);
		mainController = new UIController();
	}

	public static Stage getMainStage() {
		return stage;
	}

}
