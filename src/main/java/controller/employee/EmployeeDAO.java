package controller.employee;

import java.util.List;
import java.util.Optional;

import model.Employee;

public interface EmployeeDAO {

	Optional<Employee> findByPrimaryKey(int code);

	void insert(Employee employee);

	void update(Employee employee);

	void delete(Employee employee);

	List<Employee> getAll();

}
