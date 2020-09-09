package controller.excavation;

import java.util.List;
import java.util.Optional;

import model.Excavation;

public interface ExcavationDAO {

	Optional<Excavation> findByPrimaryKey(int code);

	void insert(Excavation excavation);

	void update(Excavation excavation);

	List<Excavation> getAll();

}
