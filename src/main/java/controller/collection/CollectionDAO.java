package controller.collection;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import controller.exposition.ExpositionDAO;
import model.Collection;

public interface CollectionDAO {

	Optional<Collection> findByPrimaryKey(int code);

	void insert(Collection collection);

	void update(Collection collection);

	/**
	 * Used by {@link ExpositionDAO}
	 *
	 * @param result
	 * @return
	 */
	Collection createCollection(ResultSet result);

	List<Collection> getAll();
}
