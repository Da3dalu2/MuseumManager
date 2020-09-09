package controller.exposition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.collection.CollectionDAO;
import controller.collection.CollectionDAOImpl;
import controller.excavation.ExcavationDAOImpl;
import controller.utils.DbUtils;
import javafx.scene.control.Alert;
import model.Collection;
import model.Exposition;

public class ExpositionDAOImpl implements ExpositionDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcavationDAOImpl.class);
	private final String tableName = "MOSTRA";

	@Override
	public Optional<Exposition> findByPrimaryKey(int code) {
		Optional<Exposition> exposition = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE CodiceMostra=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				exposition = Optional.of(createExposition(result));
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

		return exposition;
	}

	@Override
	public void insert(Exposition exposition) {

		if (findByPrimaryKey(Integer.parseInt(exposition.getId())).isPresent()) {
			final Exception e = new Exception("Exposition already exists");
			LOGGER.error("Error " + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Una mostra con questo codice già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (CodiceMostra, Denominazione, Descrizione, DataInizio, DataFine, CodiceCuratore) values (?,?,?,?,?,?)";

		loadExposition(exposition, insert, false);
	}

	@Override
	public void update(Exposition exposition) {

		if (findByPrimaryKey(Integer.parseInt(exposition.getId())).isPresent()) {
			final Exception e = new Exception("Exposition does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName
				+ " SET CodiceMostra=?, Denominazione=?, Descrizione=?, Datainizio=?, DataFine=?, CodiceCuratore=? "
				+ "WHERE CodiceMostra=?";

		loadExposition(exposition, update, true);

	}

	@Override
	public List<Exposition> getAll() {

		List<Exposition> expositionsList = null;
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		final String query = "SELECT * FROM " + tableName;

		try {
			statement = connection.prepareStatement(query);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				expositionsList = new LinkedList<>();
				final Exposition exposition = createExposition(result);
				expositionsList.add(exposition);
			}

			while (result.next()) {
				final Exposition exposition = createExposition(result);
				expositionsList.add(exposition);
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

		return expositionsList;
	}

	private Exposition createExposition(ResultSet result) {
		Exposition exposition = null;
		try {
			exposition = new Exposition();
			exposition.setId(String.valueOf(result.getInt("CodiceMostra")));
			exposition.setName(result.getString("Denominazione"));
			exposition.setDescription(result.getString("Descrizione"));
			exposition.setBeginDate(String.valueOf(result.getDate("DataInizio")));
			exposition.setEndDate(String.valueOf(result.getDate("DataFine")));
			exposition.setCuratorId(String.valueOf(result.getInt("CodiceCuratore")));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return exposition;

	}

	private void loadExposition(Exposition exposition, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(exposition.getId()));
			statement.setString(2, exposition.getName());
			statement.setString(3, exposition.getDescription());
			statement.setDate(4, DbUtils.convertStringtoDate(exposition.getBeginDate()));
			statement.setDate(5, DbUtils.convertStringtoDate(exposition.getEndDate()));
			statement.setInt(6, Integer.parseInt(exposition.getCuratorId()));
			if (isUpdate) {
				statement.setInt(7, Integer.parseInt(exposition.getId()));
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

	@Override
	public List<Collection> getExposedCollections(int code) {
		final CollectionDAO collectionDAO = new CollectionDAOImpl();
		List<Collection> collectionsList = null;

		final String query = "SELECT * FROM COLLEZIONE WHERE CodiceCollezione = ANY(" + "SELECT ec.CodiceCollezione "
				+ "FROM ESPOSIZIONE_COLLEZIONE ec, MOSTRA m " + "WHERE m.CodiceMostra = ec.CodiceMostra "
				+ "AND m.CodiceMostra = ?)";

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			result = statement.executeQuery();
			if (result.next()) {
				collectionsList = new LinkedList<>();
				final Collection collection = collectionDAO.createCollection(result);
				collectionsList.add(collection);
			}

			while (result.next()) {
				final Collection collection = collectionDAO.createCollection(result);
				collectionsList.add(collection);
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

		return collectionsList;
	}
}
