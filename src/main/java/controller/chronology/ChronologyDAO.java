package controller.chronology;

import java.util.List;
import java.util.Optional;

import model.Chronology;

public interface ChronologyDAO {

	Optional<Chronology> findByPrimaryKey(int code);

	void insert(Chronology chronology);

	void update(Chronology chronology);

	List<Chronology> getAll();

}
