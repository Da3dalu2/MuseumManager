package controller.restoration;

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
import model.Restoration;

public class RestorationDAOImpl implements RestorationDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestorationDAOImpl.class);
	private final String tableName = "RESTAURO";

	@Override
	public Optional<Restoration> findByPrimaryKey(int code1, int code2) {
		Optional<Restoration> restoration = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE CodiceArchivio=? AND CodiceIntervento=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code1);
			statement.setInt(2, code2);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				restoration = Optional.of(createRestoration(result));
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
		return restoration;

	}

	private Restoration createRestoration(ResultSet result) {
		Restoration restoration = null;
		try {
			restoration = new Restoration();
			restoration.setArtworkId(String.valueOf(String.valueOf(result.getInt("CodiceArchivio"))));
			restoration.setId(String.valueOf(String.valueOf(result.getInt("CodiceIntervento"))));
			restoration.setDescription(result.getString("Descrizione"));
			restoration.setBeginDate(String.valueOf(result.getDate("DataInizio")));
			restoration.setEndDate(String.valueOf(result.getDate("DataFine")));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return restoration;
	}

	@Override
	public void insert(Restoration restoration) {
		final int code1 = Integer.parseInt(restoration.getArtworkId());
		final int code2 = Integer.parseInt(restoration.getId());
		if (findByPrimaryKey(code1, code2).isPresent()) {
			final Exception e = new Exception("Excavation already exists");
			LOGGER.error("Errore" + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Uno scavo con questo codice già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (CodiceArchivio, CodiceIntervento, Descrizione, DataInizio, DataFine) values (?,?,?,?,?)";

		loadRestoration(restoration, insert, false);
	}

	@Override
	public void update(Restoration restoration) {
		final int code1 = Integer.parseInt(restoration.getArtworkId());
		final int code2 = Integer.parseInt(restoration.getId());
		if (findByPrimaryKey(code1, code2).isPresent()) {
			final Exception e = new Exception("Excavation does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName
				+ " SET CodiceArchivio=?, CodiceIntervento=?, Descrizione=?, DataInizio=?, DataFine=?"
				+ "WHERE CodiceArchivio=? AND CodiceIntervento=?";

		loadRestoration(restoration, update, true);

	}

	private void loadRestoration(Restoration restoration, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(restoration.getArtworkId()));
			statement.setInt(2, Integer.parseInt(restoration.getId()));
			statement.setString(3, restoration.getDescription());
			statement.setDate(4, DbUtils.convertStringtoDate(restoration.getBeginDate()));
			statement.setDate(5, DbUtils.convertStringtoDate(restoration.getEndDate()));

			if (isUpdate) {
				statement.setInt(6, Integer.parseInt(restoration.getArtworkId()));
				statement.setInt(7, Integer.parseInt(restoration.getId()));
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
	public List<Restoration> getAll() {
		List<Restoration> restorationsList = null;
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		final String query = "SELECT * FROM " + tableName;

		try {
			statement = connection.prepareStatement(query);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				restorationsList = new LinkedList<>();
				final Restoration restoration = createRestoration(result);
				restorationsList.add(restoration);
			}
			while (result.next()) {
				final Restoration restoration = createRestoration(result);
				restorationsList.add(restoration);
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
		return restorationsList;
	}

	@Override
	public Optional<List<Restoration>> getRestaurationsByRestorer(int code) {

		Optional<List<Restoration>> restaurationsList = Optional.empty();

		final String query = "SELECT r.* FROM ESECUZIONE_RESTAURO er, RESTAURO r "
				+ "WHERE er.CodiceIntervento = r.CodiceIntervento " + "AND er.CodiceRestauratore=?";

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			result = statement.executeQuery();
			if (result.next()) {
				restaurationsList = Optional.of(new LinkedList<Restoration>());
				final Restoration restauration = createRestoration(result);
				restaurationsList.get().add(restauration);
			}

			while (result.next()) {
				final Restoration restauration = createRestoration(result);
				restaurationsList.get().add(restauration);
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

		return restaurationsList;
	}

}
