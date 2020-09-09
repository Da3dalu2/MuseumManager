package controller.artwork;

import java.util.List;
import java.util.Optional;

import model.Artwork;

public interface ArtworkDAO {

	Optional<Artwork> findByPrimaryKey(int code);

	void insert(Artwork artwork);

	void update(Artwork artwork);

	List<Artwork> getAll();
}
