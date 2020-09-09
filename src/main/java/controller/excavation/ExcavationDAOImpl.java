package controller.excavation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.utils.DbUtils;
import javafx.scene.control.Alert;
import model.Excavation;

public class ExcavationDAOImpl implements ExcavationDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcavationDAOImpl.class);
	private final String tableName = "SCAVO";

	@Override
	public Optional<Excavation> findByPrimaryKey(int code) {
		Optional<Excavation> excavation = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE CodiceScavo=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				excavation = Optional.of(createExcavation(result));
			}
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				new Exception(e.getMessage());
				LOGGER.error("Error " + e.getMessage());
			}
		}
		return excavation;
	}

	@Override
	public void insert(Excavation excavation) {

		if (findByPrimaryKey(Integer.parseInt(excavation.getId())).isPresent()) {
			final Exception e = new Exception("Excavation already exists");
			LOGGER.error("Errore" + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Uno scavo con questo codice già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (CodiceScavo, Denominazione, AnnoReperimento, LuogoReperimento) values (?,?,?,?)";

		loadExcavation(excavation, insert, false);
	}

	@Override
	public void update(Excavation excavation) {

		if (findByPrimaryKey(Integer.parseInt(excavation.getId())).isPresent()) {
			final Exception e = new Exception("Excavation does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName
				+ " SET CodiceScavo=?, Denominazione=?, AnnoReperimento=?, LuogoReperimento=? " + "WHERE CodiceScavo=?";

		loadExcavation(excavation, update, true);

	}

	@Override
	public List<Excavation> getAll() {

		List<Excavation> excavationsList = null;
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		final String query = "SELECT * FROM " + tableName;

		try {
			statement = connection.prepareStatement(query);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				excavationsList = new LinkedList<>();
				final Excavation excavation = createExcavation(result);
				excavationsList.add(excavation);
			}
			while (result.next()) {
				final Excavation excavation = createExcavation(result);
				excavationsList.add(excavation);
			}
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				new Exception(e.getMessage());
				LOGGER.error("Error " + e.getMessage());
			}
		}
		return excavationsList;
	}

	private Excavation createExcavation(ResultSet result) {
		Excavation excavation = null;
		try {
			excavation = new Excavation();
			excavation.setId(String.valueOf(String.valueOf(result.getInt("CodiceScavo"))));
			excavation.setName(result.getString("Denominazione"));
			excavation.setYearRetrival(String.valueOf(result.getDate("AnnoReperimento").toLocalDate().getYear()));
			excavation.setPlaceRetrival(result.getString("LuogoReperimento"));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return excavation;
	}

	private void loadExcavation(Excavation excavation, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(excavation.getId()));
			statement.setString(2, excavation.getName());
			statement.setInt(3, Integer.parseInt(excavation.getYearRetrival()));
			statement.setString(4, excavation.getPlaceRetrival());
			if (isUpdate) {
				statement.setInt(5, Integer.parseInt(excavation.getId()));
			}
			statement.executeUpdate();

		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				new Exception(e.getMessage());
				LOGGER.error("Error " + e.getMessage());
			}
		}
	}
}
