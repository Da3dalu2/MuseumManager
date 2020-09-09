package view.controllers.archive;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.exposition.ExpositionDAO;
import controller.exposition.ExpositionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Exposition;

public class ExpositionArchiveController implements Initializable {

	@FXML
	private TextField expositionIdSearchText;

	@FXML
	private RadioButton showCurrentExpositions;

	@FXML
	private Button expositionIdSearch;

	@FXML
	private TableView<Exposition> expositionsTable;

	@FXML
	private TableColumn<Exposition, String> expoIdCol;

	@FXML
	private TableColumn<Exposition, String> expoNameCol;

	@FXML
	private TableColumn<Exposition, String> expoDescrCol;

	@FXML
	private TableColumn<Exposition, String> expoBeginDateCol;

	@FXML
	private TableColumn<Exposition, String> expoEndDateCol;

	@FXML
	private TableColumn<Exposition, String> expoCuratorIdCol;

	private ExpositionDAO expositionDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		expositionDAO = new ExpositionDAOImpl();

		expositionIdSearch.setOnAction(e -> {
			if (expositionIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceMostra deve essere non nullo!");
				alert.show();
			} else {
				final Optional<Exposition> exposition = expositionDAO
						.findByPrimaryKey(Integer.parseInt(expositionIdSearchText.getText()));
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (exposition.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questa mostra");
				} else {
					alert.setContentText("Informazioni sulla mostra:\n" + exposition.get());
				}

				alert.show();
			}
		});

		loadTableData();

	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Exposition> expositionsList = FXCollections.observableList(expositionDAO.getAll());
		expositionsTable.setItems(expositionsList);

		expoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		expoNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		expoDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		expoBeginDateCol.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
		expoEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		expoCuratorIdCol.setCellValueFactory(new PropertyValueFactory<>("curatorId"));

		expositionsTable.getColumns().setAll(expoIdCol, expoNameCol, expoDescrCol, expoBeginDateCol, expoEndDateCol,
				expoCuratorIdCol);

		/*
		 * expoIdCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		 * expoIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition,
		 * String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Exposition, String> t) { final
		 * Exposition updated = expositionsList.get(t.getTablePosition().getRow());
		 * updated.setId(t.getNewValue()); expositionDAO.update(updated); // Synchronize
		 * changes to the database } });
		 */

		expoNameCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		expoNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition, String>>() {
			@Override
			public void handle(CellEditEvent<Exposition, String> t) {
				final Exposition updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setName(t.getNewValue());
				expositionDAO.update(updated);
			}
		});

		expoNameCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		expoNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition, String>>() {
			@Override
			public void handle(CellEditEvent<Exposition, String> t) {
				final Exposition updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setDescription(t.getNewValue());
				expositionDAO.update(updated);
			}
		});

		expoDescrCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		expoDescrCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition, String>>() {
			@Override
			public void handle(CellEditEvent<Exposition, String> t) {
				final Exposition updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setBeginDate(t.getNewValue());
				expositionDAO.update(updated);
			}
		});

		expoIdCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		expoIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition, String>>() {
			@Override
			public void handle(CellEditEvent<Exposition, String> t) {
				final Exposition updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setEndDate(t.getNewValue());
				expositionDAO.update(updated); // Synchronize changes to the database
			}
		});

		expoDescrCol.setCellFactory(TextFieldTableCell.<Exposition>forTableColumn());
		expoDescrCol.setOnEditCommit(new EventHandler<CellEditEvent<Exposition, String>>() {
			@Override
			public void handle(CellEditEvent<Exposition, String> t) {
				final Exposition updated = expositionsList.get(t.getTablePosition().getRow());
				updated.setCuratorId(t.getNewValue());
				expositionDAO.update(updated);
			}
		});

	}

}
