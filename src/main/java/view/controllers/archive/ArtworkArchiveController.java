package view.controllers.archive;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.artwork.ArtworkDAO;
import controller.artwork.ArtworkDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Artwork;

public class ArtworkArchiveController implements Initializable {

	@FXML
	private TableView<Artwork> artworksTable;

	@FXML
	private TableColumn<Artwork, String> artIdCol;

	@FXML
	private TableColumn<Artwork, String> artNameCol;

	@FXML
	private TableColumn<Artwork, String> artConsStateCol;

	@FXML
	private TableColumn<Artwork, String> artMaterialCol;

	@FXML
	private TableColumn<Artwork, String> artTecniqueCol;

	@FXML
	private TableColumn<Artwork, String> artUnitCol;

	@FXML
	private TableColumn<Artwork, String> artHeightCol;

	@FXML
	private TableColumn<Artwork, String> artWidthCol;

	@FXML
	private TableColumn<Artwork, String> artDepthCol;

	@FXML
	private TableColumn<Artwork, String> artChronoIdCol;

	@FXML
	private TableColumn<Artwork, String> artExcIdCol;

	@FXML
	private TableColumn<Artwork, String> artArchivistIdCol;

	@FXML
	private TableColumn<Artwork, String> artCurrentColCol;

	@FXML
	private TextField artworkIdSearchText;

	@FXML
	private Button artworkIdSearch;

	private ArtworkDAO artworkDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		artworkDAO = new ArtworkDAOImpl();

		artworkIdSearch.setOnAction(e -> {
			if (artworkIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceArchivio deve essere non nullo!");
				alert.show();
			} else {
				final Optional<Artwork> artwork = artworkDAO
						.findByPrimaryKey(Integer.parseInt(artworkIdSearchText.getText()));
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (artwork.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questa opera");
				} else {
					alert.setContentText("Informazioni sull'opera:\n" + artwork.get());
				}

				alert.show();
			}
		});

		loadTableData();

	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Artwork> artworksList = FXCollections.observableList(artworkDAO.getAll());
		artworksTable.setItems(artworksList);

		artIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		artNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		artConsStateCol.setCellValueFactory(new PropertyValueFactory<>("conservationState"));
		artMaterialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
		artTecniqueCol.setCellValueFactory(new PropertyValueFactory<>("tecnique"));
		artUnitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		artHeightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
		artWidthCol.setCellValueFactory(new PropertyValueFactory<>("width"));
		artDepthCol.setCellValueFactory(new PropertyValueFactory<>("depth"));
		artChronoIdCol.setCellValueFactory(new PropertyValueFactory<>("chronologyId"));
		artExcIdCol.setCellValueFactory(new PropertyValueFactory<>("excavationId"));
		artArchivistIdCol.setCellValueFactory(new PropertyValueFactory<>("archivistId"));
		artCurrentColCol.setCellValueFactory(new PropertyValueFactory<>("currentCollectionId"));

		artworksTable.getColumns().setAll(artIdCol, artNameCol, artMaterialCol, artTecniqueCol, artUnitCol,
				artHeightCol, artWidthCol, artDepthCol, artChronoIdCol, artExcIdCol, artArchivistIdCol,
				artCurrentColCol);

		/*
		 * artIdCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		 * artIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
		 *
		 * @Override public void handle(CellEditEvent<Artwork, String> t) { final
		 * Artwork updated = artworksList.get(t.getTablePosition().getRow());
		 * updated.setId(t.getNewValue()); artworkDAO.update(updated); // Synchronize
		 * changes to the database } });
		 */

		artNameCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artConsStateCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artConsStateCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artMaterialCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artMaterialCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artTecniqueCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artTecniqueCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artUnitCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artUnitCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artHeightCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artHeightCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artWidthCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artWidthCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artDepthCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artDepthCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artChronoIdCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artChronoIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artExcIdCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artExcIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		artArchivistIdCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn());
		artArchivistIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork, String>>() {
			@Override
			public void handle(CellEditEvent<Artwork, String> t) {
				final Artwork updated = artworksList.get(t.getTablePosition().getRow());
				updated.setId(t.getNewValue());
				artworkDAO.update(updated);
			}
		});

		/*
		 * artCurrentColCol.setCellFactory(TextFieldTableCell.<Artwork>forTableColumn())
		 * ; artCurrentColCol.setOnEditCommit(new EventHandler<CellEditEvent<Artwork,
		 * String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Artwork, String> t) { final
		 * Artwork updated = artworksList.get(t.getTablePosition().getRow());
		 * updated.setId(t.getNewValue()); artworkDAO.update(updated); } });
		 */
	}
}
