package view.controllers.archivist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Artwork;

public class ArtworkCatalogationController implements Initializable {

	@FXML
	private TextField artworkId;

	@FXML
	private TextArea artworkDefinition;

	@FXML
	private TextField artworkChronologyId;

	@FXML
	private TextField artworkMaterial;

	@FXML
	private TextField artworkArchivistId;

	@FXML
	private TextField conservationState;

	@FXML
	private TextField artworkHeight;

	@FXML
	private TextField artworkExcavationId;

	@FXML
	private TextField artworkTechnique;

	@FXML
	private TextField artworkUnit;

	@FXML
	private TextField artworkWidth;

	@FXML
	private TextField artworkDepth;

	@FXML
	private Button insertArtwork;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		insertArtwork.setOnAction(e -> {

			final String id = artworkId.getText();
			final String name = artworkDefinition.getText();
			final String consState = conservationState.getText();
			final String material = artworkMaterial.getText();
			final String tecnique = artworkTechnique.getText();
			final String unit = artworkUnit.getText();
			final String height = artworkHeight.getText();
			final String width = artworkWidth.getText();
			final String depth = artworkDepth.getText();
			final String excavationId = artworkExcavationId.getText();
			final String chronologyId = artworkChronologyId.getText();
			final String archivistId = artworkArchivistId.getText();

			if (id.isEmpty() || name.isEmpty() || consState.isEmpty() || material.isEmpty() || tecnique.isEmpty()
					|| unit.isEmpty() || height.isEmpty() || width.isEmpty() || depth.isEmpty()
					|| excavationId.isEmpty() || chronologyId.isEmpty() || archivistId.isEmpty()) {

				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Tutti i parametri obbligatori devono essere non nulli!");

				alert.show();
			} else {
				final Artwork artwork = new Artwork();
				artwork.setId(id);
				artwork.setName(name);
				artwork.setConservationState(consState);
				artwork.setMaterial(material);
				artwork.setTecnique(tecnique);
				artwork.setUnit(unit);
				artwork.setHeight(height);
				artwork.setWidth(width);
				artwork.setDepth(depth);
				artwork.setExcavationId(excavationId);
				artwork.setChronologyId(chronologyId);
				artwork.setArchivistId(archivistId);

			}
		});
	}
}
