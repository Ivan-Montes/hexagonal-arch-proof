package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Artist;

public interface ArtistProducerPort {

	void sendArtistCreatedMessage(Artist artist);
	void sendArtistUpdatedMessage(Artist artist);
	void sendArtistDeletedMessage(Artist artist);
	
}
