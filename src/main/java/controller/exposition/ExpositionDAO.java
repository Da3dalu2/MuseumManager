package controller.exposition;

import java.util.List;
import java.util.Optional;

import controller.collection.CollectionDAO;
import model.Collection;
import model.Exposition;

public interface ExpositionDAO {

	Optional<Exposition> findByPrimaryKey(int code);

	void insert(Exposition exposition);

	void update(Exposition exposition);

	List<Exposition> getAll();

	/**
	 * Uses {@link CollectionDAO}
	 *
	 * @param code
	 * @return
	 */
	List<Collection> getExposedCollections(int code);
}
