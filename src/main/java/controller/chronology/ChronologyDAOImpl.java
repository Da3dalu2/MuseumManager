package controller.chronology;

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
import model.Chronology;

public class ChronologyDAOImpl implements ChronologyDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChronologyDAOImpl.class);
	private final String tableName = "CRONOLOGIA";

	@Override
	public Optional<Chronology> findByPrimaryKey(int code) {
		Optional<Chronology> chronology = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE IdCronologia=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				chronology = Optional.of(createChronology(result));

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
		return chronology;
	}

	private Chronology createChronology(ResultSet result) {
		Chronology chronology = null;
		try {
			chronology = new Chronology();
			chronology.setId(String.valueOf(result.getInt("IdCronologia")));
			chronology.setChronologicalFraction(result.getString("FrazioneCronologica"));
			chronology.setReferenceSlot(result.getString("FasciaRiferimento"));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return chronology;

	}

	@Override
	public void insert(Chronology chronology) {
		if (findByPrimaryKey(Integer.parseInt(chronology.getId())).isPresent()) {
			final Exception e = new Exception("Chronology already exists");
			LOGGER.error("Error " + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Una cronologia con questo identificativo già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (IdCronologia, FasciaRiferimento, FrazioneCronologica) values (?,?,?)";

		loadChronology(chronology, insert, false);

	}

	@Override
	public void update(Chronology chronology) {
		if (findByPrimaryKey(Integer.parseInt(chronology.getId())) == null) {
			final Exception e = new Exception("Chronology does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName
				+ " SET IdCronologia=?, FasciaRiferimento=?, FrazioneCronologica=? WHERE IdCronologia=?";

		loadChronology(chronology, update, true);
	}

	@Override
	public List<Chronology> getAll() {
		List<Chronology> chronologyList = null;

		final String query = "SELECT * FROM " + tableName;

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			if (result.next()) {
				chronologyList = new LinkedList<>();
				final Chronology chronology = createChronology(result);
				chronologyList.add(chronology);
			}

			while (result.next()) {
				final Chronology chronology = createChronology(result);
				chronologyList.add(chronology);
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

		return chronologyList;
	}

	private void loadChronology(Chronology chronology, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(chronology.getId()));
			statement.setString(2, chronology.getReferenceSlot());
			statement.setString(3, chronology.getChronologicalFraction());
			if (isUpdate) {
				statement.setString(4, chronology.getId());
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
