package controller.utils;

import static controller.utils.DbParams.DBURI;
import static controller.utils.DbParams.DRIVER;
import static controller.utils.DbParams.PASSWORD;
import static controller.utils.DbParams.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DbUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

	public static Connection getDbConnection() {
		final Connection connection = getMySQLConnection();
		return connection;
	}

	private static Connection getMySQLConnection() {

		Optional<Connection> connection = Optional.empty();
		try {

			LOGGER.info("DataSource.getConnection() driver = " + DRIVER);
			// Class.forName(DRIVER); registration not needed
			LOGGER.info("DataSource.getConnection() dbUri = " + DBURI);
			connection = Optional.of(DriverManager.getConnection(DBURI, USERNAME, PASSWORD));
		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}

		return connection.get();
	}

	public static java.sql.Date convertStringtoDate(String string) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed = null;
		try {
			parsed = format.parse(string);
		} catch (final ParseException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		final java.sql.Date sql = new java.sql.Date(parsed.getTime());
		return sql;
	}

	/*
	 * public static ResultSet executeQuery(String query) { final Connection
	 * connection = DbUtils.getDbConnection(); PreparedStatement statement = null;
	 * ResultSet result = null; try { statement =
	 * connection.prepareStatement(query); result = statement.executeQuery(); }
	 * catch (final SQLException e) { new Exception(e.getMessage());
	 * LOGGER.error("Error " + e.getMessage()); } finally { try { if (statement !=
	 * null) { statement.close(); } if (connection != null) { connection.close(); }
	 * } catch (final SQLException e) { new Exception(e.getMessage());
	 * LOGGER.error("Error " + e.getMessage()); } }
	 *
	 * return result;
	 *
	 * }
	 */
}
