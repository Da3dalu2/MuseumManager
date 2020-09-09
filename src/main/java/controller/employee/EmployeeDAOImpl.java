package controller.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.utils.DbUtils;
import javafx.scene.control.Alert;
import model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	private final Map<String, String> tablesMap;
	private final Map<String, String> idsMap;
	private final String tableName = "DIPENDENTI";

	public EmployeeDAOImpl() {
		tablesMap = new HashMap<>();
		tablesMap.put("restauratore", "RESTAURATORE");
		tablesMap.put("archivista", "ARCHIVISTA");
		tablesMap.put("curatore", "CURATORE");
		tablesMap.put("registrar", "REGISTRAR");

		idsMap = new HashMap<>();
		idsMap.put("restauratore", "CodiceRestauratore");
		idsMap.put("archivista", "CodiceArchivista");
		idsMap.put("curatore", "CodiceCuratore");
		idsMap.put("registrar", "CodiceRegistrar");
	}

	@Override
	public Optional<Employee> findByPrimaryKey(int code) {
		Optional<Employee> employee = Optional.empty();

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		final String query = "SELECT * FROM " + tableName + " WHERE Identificativo=?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				employee = Optional.of(createEmployee(result));

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
		return employee;
	}

	private Employee createEmployee(ResultSet result) {
		Employee employee = null;
		try {
			employee = new Employee();
			employee.setId(String.valueOf(result.getInt("Identificativo")));
			employee.setName(result.getString("Nome"));
			employee.setSurname(result.getString("Cognome"));
			employee.setPhone(String.valueOf(result.getString("Telefono")));
			// employee.setTipology(String.valueOf(tipology.get()));

		} catch (final SQLException e) {
			new Exception(e.getMessage());
			LOGGER.error("Error " + e.getMessage());
		}
		return employee;
	}

	@Override
	public void insert(Employee employee) {
		if (findByPrimaryKey(Integer.parseInt(employee.getId())).isPresent()) {
			final Exception e = new Exception("Employee already exists");
			LOGGER.error("Error " + e.getMessage());
			final Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Un dipendente con questo identificativo già esiste!");
			alert.show();
		}

		final String tableName = tablesMap.get(employee.getTipology());
		final String employeeId = idsMap.get(employee.getTipology());

		final String insert = "INSERT INTO " + tableName + " (" + employeeId
				+ ", Nome, Cognome, Telefono) values (?,?,?,?)";

		loadEmployee(employee, insert, false);
	}

	@Override
	public void delete(Employee employee) {
		final String tableName = tablesMap.get(employee.getTipology());
		final String employeeId = idsMap.get(employee.getTipology());
		final String delete = "DELETE FROM " + tableName + "WHERE " + employeeId + "=?";

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(delete);
			statement.setInt(1, Integer.parseInt(employee.getId()));
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
	public void update(Employee employee) {
		if (findByPrimaryKey(Integer.parseInt(employee.getId())) == null) {
			final Exception e = new Exception("Employee does not exists");
			LOGGER.error("Error " + e.getMessage());
		}

		final String tableName = tablesMap.get(employee.getTipology());
		final String employeeId = idsMap.get(employee.getTipology());

		final String update = "UPDATE " + tableName + " SET " + employeeId + "=?, Nome=?, Cognome=?, Telefono=? WHERE "
				+ employeeId + "=?";

		loadEmployee(employee, update, true);
	}

	private void loadEmployee(Employee employee, String query, boolean isUpdate) {
		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(employee.getId()));
			statement.setString(2, employee.getName());
			statement.setString(3, employee.getSurname());
			statement.setString(4, employee.getPhone());
			if (isUpdate) {
				statement.setString(5, employee.getId());
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
	public List<Employee> getAll() {
		List<Employee> employeeList = null;

		final String query = "SELECT * FROM " + tableName;

		final Connection connection = DbUtils.getDbConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			if (result.next()) {
				employeeList = new LinkedList<>();
				final Employee chronology = createEmployee(result);
				employeeList.add(chronology);
			}

			while (result.next()) {
				final Employee chronology = createEmployee(result);
				employeeList.add(chronology);
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

		return employeeList;
	}

}
