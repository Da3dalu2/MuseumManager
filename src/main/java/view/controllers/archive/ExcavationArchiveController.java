package view.controllers.archive;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.excavation.ExcavationDAO;
import controller.excavation.ExcavationDAOImpl;
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
import model.Excavation;

public class ExcavationArchiveController implements Initializable {

	@FXML
	private TableView<Excavation> excavationsTable;

	@FXML
	private TableColumn<Excavation, String> excIdCol;

	@FXML
	private TableColumn<Excavation, String> excNameCol;

	@FXML
	private TableColumn<Excavation, String> excYearRetrivalCol;

	@FXML
	private TableColumn<Excavation, String> excPlaceRetrivalCol;

	@FXML
	private TextField excavationIdSearchText;

	@FXML
	private Button excavationIdSearch;

	private ExcavationDAO excavationDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		excavationDAO = new ExcavationDAOImpl();

		excavationIdSearch.setOnAction(e -> {
			if (excavationIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceScavo deve essere non nullo!");
				alert.show();
			} else {
				final Optional<Excavation> excavation = excavationDAO
						.findByPrimaryKey(Integer.parseInt(excavationIdSearchText.getText()));
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (excavation.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questo scavo");
				} else {
					alert.setContentText("Informazioni sullo scavo:\n" + excavation.get());
				}

				alert.show();
			}
		});

		loadTableData();

	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Excavation> excavationsList = FXCollections.observableList(excavationDAO.getAll());
		excavationsTable.setItems(excavationsList);

		excIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		excNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		excYearRetrivalCol.setCellValueFactory(new PropertyValueFactory<>("yearRetrival"));
		excPlaceRetrivalCol.setCellValueFactory(new PropertyValueFactory<>("placeRetrival"));

		excavationsTable.getColumns().setAll(excIdCol, excNameCol, excYearRetrivalCol, excPlaceRetrivalCol);

		/*
		 * excIdCol.setCellFactory(TextFieldTableCell.<Excavation>forTableColumn());
		 * excIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Excavation,
		 * String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Excavation, String> t) { final
		 * Excavation updatedExcavation =
		 * excavationsList.get(t.getTablePosition().getRow());
		 * updatedExcavation.setId(t.getNewValue());
		 * excavationDAO.update(updatedExcavation); // Synchronize changes to the
		 * database } });
		 */

		excNameCol.setCellFactory(TextFieldTableCell.<Excavation>forTableColumn());
		excNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Excavation, String>>() {
			@Override
			public void handle(CellEditEvent<Excavation, String> t) {
				final Excavation updatedExcavation = excavationsList.get(t.getTablePosition().getRow());
				updatedExcavation.setName(t.getNewValue());
				excavationDAO.update(updatedExcavation);
			}
		});

		excYearRetrivalCol.setCellFactory(TextFieldTableCell.<Excavation>forTableColumn());
		excYearRetrivalCol.setOnEditCommit(new EventHandler<CellEditEvent<Excavation, String>>() {
			@Override
			public void handle(CellEditEvent<Excavation, String> t) {
				final Excavation updatedExcavation = excavationsList.get(t.getTablePosition().getRow());
				updatedExcavation.setYearRetrival(t.getNewValue());
				excavationDAO.update(updatedExcavation);
			}
		});

		excPlaceRetrivalCol.setCellFactory(TextFieldTableCell.<Excavation>forTableColumn());
		excPlaceRetrivalCol.setOnEditCommit(new EventHandler<CellEditEvent<Excavation, String>>() {
			@Override
			public void handle(CellEditEvent<Excavation, String> t) {
				final Excavation updatedExcavation = excavationsList.get(t.getTablePosition().getRow());
				updatedExcavation.setPlaceRetrival(t.getNewValue());
				excavationDAO.update(updatedExcavation);
			}
		});
	}
}
