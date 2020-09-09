package controller.collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.excavation.ExcavationDAOImpl;
import controller.utils.DbUtils;
import javafx.scene.control.Alert;
import model.Collection;

public class CollectionDAOImpl implements CollectionDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcavationDAOImpl.class);
	private final String tableName = "COLLEZIONE";

	@Override
	public Optional<Collection> findByPrimaryKey(int code) {

		Optional<Collection> collection = null;
		final String query = "SELECT * FROM " + tableName + " WHERE CodiceCollezione=?";
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				collection = Optional.of(createCollection(result));
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

		return collection;
	}

	@Override
	public void insert(Collection collection) {

		if (findByPrimaryKey(Integer.parseInt(collection.getId())).isPresent()) {
			final Exception e = new Exception("Collection already exists");
			LOGGER.error("Error " + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Una collezione con questo codice già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (CodiceCollezione, Denominazione, Descrizione, Dimensione, CodiceCuratore) values (?,?,?,?,?)";

		loadCollection(collection, insert, false);
	}

	@Override
	public void update(Collection collection) {

		if (findByPrimaryKey(Integer.parseInt(collection.getId())) == null) {
			final Exception e = new Exception("Excavation does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName
				+ " SET CodiceScavo=?, Denominazione=?, AnnoReperimento=?, LuogoReperimento=? WHERE CodiceScavo=?";

		loadCollection(collection, update, true);

	}

	@Override
	public List<Collection> getAll() {
		final CollectionDAO collectionDAO = new CollectionDAOImpl();
		List<Collection> collectionsList = null;

		final String query = "SELECT * FROM " + tableName;
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
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

	@Override
	public Collection createCollection(ResultSet result) {
		Collection collection = null;
		try {
			collection = new Collection();
			collection.setId(String.valueOf(result.getInt("CodiceCollezione")));
			collection.setName(result.getString("Denominazione"));
			collection.setDescription(result.getString("Descrizione"));
			collection.setDimension(String.valueOf(result.getInt("Dimensione")));
			collection.setCuratorId(String.valueOf(result.getInt("CodiceCuratore")));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return collection;

	}

	private void loadCollection(Collection collection, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(collection.getId()));
			statement.setString(2, collection.getName());
			statement.setString(3, collection.getDescription());
			statement.setInt(4, Integer.parseInt(collection.getDimension()));
			statement.setInt(5, Integer.parseInt(collection.getCuratorId()));
			if (isUpdate) {
				statement.setInt(6, Integer.parseInt(collection.getId()));
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
