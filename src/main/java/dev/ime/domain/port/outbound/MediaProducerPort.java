package dev.ime.domain.port.outbound;

import dev.ime.domain.model.Media;

public interface MediaProducerPort {

	void sendMediaCreatedMessage(Media media);
	void sendMediaUpdatedMessage(Media media);
	void sendMediaDeletedMessage(Media media);
	
}
