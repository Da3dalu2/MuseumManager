package view.controllers.archive;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.chronology.ChronologyDAO;
import controller.chronology.ChronologyDAOImpl;
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
import model.Chronology;

public class ChronologyArchiveController implements Initializable {

	@FXML
	private TableView<Chronology> chronologyTable;

	@FXML
	private TableColumn<Chronology, String> chronoIdCol;

	@FXML
	private TableColumn<Chronology, String> chronoRefSlotCol;

	@FXML
	private TableColumn<Chronology, String> chronoFractionCol;

	@FXML
	private TextField chronologyIdSearchText;

	@FXML
	private Button chronologyIdSearch;

	private ChronologyDAO chronologyDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		chronologyDAO = new ChronologyDAOImpl();

		chronologyIdSearch.setOnAction(e -> {
			if (chronologyIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceScavo deve essere non nullo!");
				alert.show();
			} else {
				final Optional<Chronology> chronology = chronologyDAO
						.findByPrimaryKey(Integer.parseInt(chronologyIdSearchText.getText()));
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (chronology.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questo scavo");
				} else {
					alert.setContentText("Informazioni sullo scavo:\n" + chronology.get());
				}

				alert.show();
			}
		});

		loadTableData();

	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Chronology> chronologyList = FXCollections.observableList(chronologyDAO.getAll());
		chronologyTable.setItems(chronologyList);

		chronoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		chronoRefSlotCol.setCellValueFactory(new PropertyValueFactory<>("referenceSlot"));
		chronoFractionCol.setCellValueFactory(new PropertyValueFactory<>("chronologicalFraction"));

		chronologyTable.getColumns().setAll(chronoIdCol, chronoRefSlotCol, chronoFractionCol);

		/*
		 * chronoIdCol.setCellFactory(TextFieldTableCell.<Chronology>forTableColumn());
		 * chronoIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Chronology,
		 * String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Chronology, String> t) { final
		 * Chronology updated = chronologyList.get(t.getTablePosition().getRow());
		 * updated.setId(t.getNewValue()); chronologyDAO.update(updated); // Synchronize
		 * changes to the database } });
		 */

		chronoRefSlotCol.setCellFactory(TextFieldTableCell.<Chronology>forTableColumn());
		chronoRefSlotCol.setOnEditCommit(new EventHandler<CellEditEvent<Chronology, String>>() {
			@Override
			public void handle(CellEditEvent<Chronology, String> t) {
				final Chronology updated = chronologyList.get(t.getTablePosition().getRow());
				updated.setReferenceSlot(t.getNewValue());
				chronologyDAO.update(updated);
			}
		});

		chronoFractionCol.setCellFactory(TextFieldTableCell.<Chronology>forTableColumn());
		chronoFractionCol.setOnEditCommit(new EventHandler<CellEditEvent<Chronology, String>>() {
			@Override
			public void handle(CellEditEvent<Chronology, String> t) {
				final Chronology updated = chronologyList.get(t.getTablePosition().getRow());
				updated.setChronologicalFraction(t.getNewValue());
				chronologyDAO.update(updated);
			}
		});

	}
}
