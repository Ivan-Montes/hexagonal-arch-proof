package dev.ime.domain.port.inbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Media;

public interface MediaPort {

	List<Media>getAll();
	Optional<Media>getById(Long id);
	Optional<Media>create(Media media);
	Optional<Media>update(Long id, Media media);
	Boolean deleteById(Long id);
	
}
