package controller.artwork;

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
import model.Artwork;

public class ArtworkDAOImpl implements ArtworkDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtworkDAOImpl.class);
	private final String tableName = "OPERA";

	@Override
	public Optional<Artwork> findByPrimaryKey(int code) {
		Optional<Artwork> artwork = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE CodiceArchivio=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				artwork = Optional.of(createArtwork(result));

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
		return artwork;
	}

	private Artwork createArtwork(ResultSet result) {
		Artwork artwork = null;
		try {
			artwork = new Artwork();
			artwork.setId(String.valueOf(result.getInt("CodiceArchivio")));
			artwork.setName(result.getString("Definizione"));
			artwork.setConservationState(result.getString("StatoConservazione"));
			artwork.setMaterial(result.getString("Materia"));
			artwork.setTecnique(result.getString("Tecnica"));
			artwork.setUnit(result.getString("Unità"));
			artwork.setHeight(String.valueOf(result.getInt("Altezza")));
			artwork.setWidth(String.valueOf(result.getInt("Larghezza")));

			final Optional<Integer> depth = Optional.of(result.getInt("Profondità"));
			if (depth.isPresent()) {
				artwork.setDepth(String.valueOf(depth.get()));
			}

			artwork.setChronologyId(String.valueOf(result.getInt("IdCronologia")));
			artwork.setExcavationId(String.valueOf(result.getInt("CodiceScavo")));
			artwork.setArchivistId(String.valueOf(result.getInt("CodiceArchivista")));
			artwork.setCurrentCollectionId(String.valueOf(result.getInt("CodiceCollezione")));

		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return artwork;
	}

	@Override
	public void insert(Artwork artwork) {
		if (findByPrimaryKey(Integer.parseInt(artwork.getId())).isPresent()) {
			final Exception e = new Exception("Chronology already exists");
			LOGGER.error("Error " + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Una cronologia con questo identificativo già esiste!");
			alert.show();
		}

		final String insert = "INSERT INTO " + tableName
				+ " (CodiceArchivio, Definizione, StatoConservazione, Materia,\n"
				+ " Tecnica, Unità, Altezza, Larghezza, Profondità, IdCronologia, CodiceScavo, "
				+ "CodiceCollezione, CodiceArchivista) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		loadArtwork(artwork, insert, false);

	}

	@Override
	public void update(Artwork artwork) {
		if (findByPrimaryKey(Integer.parseInt(artwork.getId())) == null) {
			final Exception e = new Exception("Chronology does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String update = "UPDATE " + tableName + " SET CodiceArchivio=?, Definizione=?, StatoConservazione=? "
				+ "Materia=?, Tecnica=?, Unità=?, Altezza=?, Larghezza=?, "
				+ "Profondità=?, IdCronologia=?, CodiceScavo=?, CodiceArchivista=?, CodiceCollezione=?"
				+ "WHERE CodiceArchivio=?";

		loadArtwork(artwork, update, true);

	}

	private void loadArtwork(Artwork artwork, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(artwork.getId()));
			statement.setString(2, artwork.getName());
			statement.setString(3, artwork.getConservationState());
			statement.setString(4, artwork.getMaterial());
			statement.setString(5, artwork.getTecnique());
			statement.setString(6, artwork.getUnit());
			statement.setInt(7, Integer.parseInt(artwork.getHeight()));
			statement.setInt(8, Integer.parseInt(artwork.getWidth()));
			statement.setInt(9, Integer.parseInt(artwork.getDepth()));
			statement.setInt(10, Integer.parseInt(artwork.getChronologyId()));
			statement.setInt(11, Integer.parseInt(artwork.getExcavationId()));
			statement.setInt(12, Integer.parseInt(artwork.getArchivistId()));
			statement.setInt(13, Integer.parseInt(artwork.getCurrentCollectionId()));

			if (isUpdate) {
				statement.setString(14, artwork.getId());
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
	public List<Artwork> getAll() {
		List<Artwork> artworksList = null;

		final String query = "SELECT * FROM " + tableName;

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			if (result.next()) {
				artworksList = new LinkedList<>();
				final Artwork artwork = createArtwork(result);
				artworksList.add(artwork);
			}

			while (result.next()) {
				final Artwork artwork = createArtwork(result);
				artworksList.add(artwork);
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

		return artworksList;
	}

}
