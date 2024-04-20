package dev.ime.domain.port.outbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Media;

public interface MediaRepositoryPort {

	List<Media>findAll();
	Optional<Media>findById(Long id);
	Optional<Media>save(Media media);
	void deleteById(Long id);
	
}
