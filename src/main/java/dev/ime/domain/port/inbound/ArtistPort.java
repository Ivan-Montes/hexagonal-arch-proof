package dev.ime.domain.port.inbound;

import java.util.List;
import java.util.Optional;

import dev.ime.domain.model.Artist;

public interface ArtistPort {

	List<Artist>getAll();
	Optional<Artist>getById(Long id);
	Optional<Artist>create(Artist artist);
	Optional<Artist>update(Long id, Artist artist);
	Boolean deleteById(Long id);
}
