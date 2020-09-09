package view.controllers.restorer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.employee.EmployeeDAO;
import controller.employee.EmployeeDAOImpl;
import controller.restoration.RestorationDAO;
import controller.restoration.RestorationDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import model.Restoration;

public class RestaurationVisualizationController implements Initializable {

	@FXML
	private TableView<Restoration> restaurationsTable;

	@FXML
	private TableColumn<Restoration, String> restArtworkIdCol;

	@FXML
	private TableColumn<Restoration, String> restIdCol;

	@FXML
	private TableColumn<Restoration, String> restDescrCol;

	@FXML
	private TableColumn<Restoration, String> restBeginDateCol;

	@FXML
	private TableColumn<Restoration, String> restEndDateCol;

	@FXML
	private TableView<Restoration> restorersTable;

	@FXML
	private TableColumn<Restoration, String> restorersArtworkIdCol;

	@FXML
	private TableColumn<Restoration, String> restorersIdCol;

	@FXML
	private TableColumn<Restoration, String> restorersDescrCol;

	@FXML
	private TableColumn<Restoration, String> restorersBeginDateCol;

	@FXML
	private TableColumn<Restoration, String> restorersEndDateCol;

	@FXML
	private TextField restorationIdSearchText;

	@FXML
	private Button restorationIdSearch;

	@FXML
	private TextField restorationArtworkIdSearchText;

	@FXML
	private TextField restorersIdSearchText;

	@FXML
	private Button restorersIdSearch;

	private RestorationDAO restorationDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		restorationDAO = new RestorationDAOImpl();

		restorationIdSearch.setOnAction(e -> {
			if (restorationIdSearchText.getText().isEmpty() || restorationArtworkIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceArchivio e CodiceIntervento devono essere non nulli!");
				alert.show();
			} else {
				final int code1 = Integer.parseInt(restorationIdSearchText.getText());
				final int code2 = Integer.parseInt(restorationArtworkIdSearchText.getText());
				final Optional<Restoration> restoration = restorationDAO.findByPrimaryKey(code1, code2);
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (restoration.isEmpty()) {
					alert.setContentText("Non sono disponibili informazioni su questo restauro");
				} else {
					alert.setContentText("Informazioni sul restauro:\n" + restoration.get());
				}

				alert.show();
			}
		});

		restorersIdSearch.setOnAction(e -> {
			if (restorersIdSearchText.getText().isEmpty()) {
				final Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("CodiceRestauratore deve essere non nullo!");
				alert.show();
			} else {
				final int code = Integer.parseInt(restorersIdSearchText.getText());
				final Optional<Employee> restorer = employeeDAO.findByPrimaryKey(code);
				final Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if (restorer.isEmpty() || restorationDAO.getRestaurationsByRestorer(code).isEmpty()) {
					alert.setContentText(
							"Non sono disponibili informazioni sui restauri effettuati da questo restauratore");
					alert.show();
					clearTableData();
				} else {
					showTableData(code);
				}
			}
		});

		loadTableData();

	}

	private void clearTableData() {
		final ObservableList<Restoration> restorersList = FXCollections.emptyObservableList();
		restorersTable.setItems(restorersList);
	}

	@SuppressWarnings("unchecked")
	private void showTableData(int code) {

		final ObservableList<Restoration> restorersList = FXCollections
				.observableList(restorationDAO.getRestaurationsByRestorer(code).get());
		restorersTable.setItems(restorersList);

		restorersArtworkIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		restorersIdCol.setCellValueFactory(new PropertyValueFactory<>("artworkId"));
		restorersDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		restorersBeginDateCol.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
		restorersEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		restorersTable.getColumns().setAll(restorersArtworkIdCol, restorersIdCol, restorersDescrCol,
				restorersBeginDateCol, restorersEndDateCol);
	}

	@SuppressWarnings("unchecked")
	private void loadTableData() {
		final ObservableList<Restoration> restaurationsList = FXCollections.observableList(restorationDAO.getAll());
		restaurationsTable.setItems(restaurationsList);

		restArtworkIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		restIdCol.setCellValueFactory(new PropertyValueFactory<>("artworkId"));
		restDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		restBeginDateCol.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
		restEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		restaurationsTable.getColumns().setAll(restArtworkIdCol, restIdCol, restDescrCol, restBeginDateCol,
				restEndDateCol);

	}
}
