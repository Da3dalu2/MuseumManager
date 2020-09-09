package view.controllers.archive;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.collection.CollectionDAO;
import controller.collection.CollectionDAOImpl;
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
import model.Collection;

public class CollectionArchiveController implements Initializable {

	@FXML
	private TableView<Collection> collectionsTable;

	@FXML
	private TableColumn<Collection, String> colIdCol;

	@FXML
	private TableColumn<Collection, String> colNameCol;

	@FXML
	private TableColumn<Collection, String> colDescrCol;

	@FXML
	private TableColumn<Collection, String> colDimensionCol;

	@FXML
	private TableColumn<Collection, String> colCuratorIdCol;

	@FXML
	private TextField collectionArchiveIdSearchText;

	@FXML
	private Button collectionArchiveIdSearch;

	private CollectionDAO collectionDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		collectionDAO = new CollectionDAOImpl();

		collectionArchiveIdSearch.setOnAction(e -> {
			if (collectionArchiveIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceCollezione deve essere non nullo!");
				alert.show();
			} else {
				final Optional<Collection> collection = collectionDAO
						.findByPrimaryKey(Integer.parseInt(collectionArchiveIdSearchText.getText()));
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (collection.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questa collezione");
				} else {
					alert.setContentText("Informazioni sulla collezione:\n" + collection.get());
				}

				alert.show();
			}

		});

		loadTableData();
	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Collection> expositionsList = FXCollections.observableList(collectionDAO.getAll());
		collectionsTable.setItems(expositionsList);

		colIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		colDimensionCol.setCellValueFactory(new PropertyValueFactory<>("dimension"));
		colCuratorIdCol.setCellValueFactory(new PropertyValueFactory<>("curatorId"));

		collectionsTable.getColumns().setAll(colIdCol, colNameCol, colDescrCol, colDimensionCol, colCuratorIdCol);

		/*
		 * colIdCol.setCellFactory(TextFieldTableCell.<Collection>forTableColumn());
		 * colIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Collection,
		 * String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Collection, String> t) { final
		 * Collection updated = expositionsList.get(t.getTablePosition().getRow());
		 * updated.setId(t.getNewValue()); collectionDAO.update(updated); // Synchronize
		 * changes to the database } });
		 */

		colNameCol.setCellFactory(TextFieldTableCell.<Collection>forTableColumn());
		colNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Collection, String>>() {
			@Override
			public void handle(CellEditEvent<Collection, String> t) {
				final Collection updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setName(t.getNewValue());
				collectionDAO.update(updated);
			}
		});

		colDescrCol.setCellFactory(TextFieldTableCell.<Collection>forTableColumn());
		colDescrCol.setOnEditCommit(new EventHandler<CellEditEvent<Collection, String>>() {
			@Override
			public void handle(CellEditEvent<Collection, String> t) {
				final Collection updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setDescription(t.getNewValue());
				collectionDAO.update(updated);
			}
		});

		/*
		 * colDimensionCol.setCellFactory(TextFieldTableCell.<Collection>forTableColumn(
		 * )); colDimensionCol.setOnEditCommit(new
		 * EventHandler<CellEditEvent<Collection, String>>() {
		 *
		 * @Override public void handle(CellEditEvent<Collection, String> t) { final
		 * Collection updated = expositionsList.get(t.getTablePosition().getRow());
		 * updated.setDimension(t.getNewValue()); collectionDAO.update(updated); } });
		 */

		colCuratorIdCol.setCellFactory(TextFieldTableCell.<Collection>forTableColumn());
		colCuratorIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Collection, String>>() {
			@Override
			public void handle(CellEditEvent<Collection, String> t) {
				final Collection updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setCuratorId(t.getNewValue());
				collectionDAO.update(updated); // Synchronize changes to the database
			}
		});

	}
}
