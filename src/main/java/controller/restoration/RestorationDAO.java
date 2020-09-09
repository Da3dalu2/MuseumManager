package controller.restoration;

import java.util.List;
import java.util.Optional;

import model.Restoration;

public interface RestorationDAO {

	Optional<Restoration> findByPrimaryKey(int code1, int code2);

	void insert(Restoration restoration);

	void update(Restoration restoration);

	List<Restoration> getAll();

	Optional<List<Restoration>> getRestaurationsByRestorer(int code);
}
