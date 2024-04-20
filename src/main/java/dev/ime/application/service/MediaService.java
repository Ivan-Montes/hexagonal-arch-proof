package dev.ime.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.domain.model.Artist;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.MediaPort;
import dev.ime.domain.port.outbound.ArtistRepositoryPort;
import dev.ime.domain.port.outbound.MediaProducerPort;
import dev.ime.domain.port.outbound.MediaRepositoryPort;

@Service
public class MediaService implements MediaPort{

	private final MediaRepositoryPort mediaRepository;
	private final ArtistRepositoryPort artistRepository;
	private final MediaProducerPort mediaProducerPort;
	
	public MediaService(MediaRepositoryPort mediaRepository, ArtistRepositoryPort artistRepository, MediaProducerPort mediaProducerPort) {
		super();
		this.mediaRepository = mediaRepository;
		this.artistRepository = artistRepository;
		this.mediaProducerPort = mediaProducerPort;
	}

	@Override
	public List<Media> getAll() {
		
		return mediaRepository.findAll();		
	}

	@Override
	public Optional<Media> getById(Long id) {
		
		return Optional.ofNullable( mediaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID, String.valueOf(id)))));
	}

	@Override
	public Optional<Media> create(Media media) {
		
		if (artistRepository.findById(media.getArtist().getId()).isEmpty())  throw new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(media.getArtist().getId())));
		
		Optional<Media> optMediaCreated = mediaRepository.save(media);
		optMediaCreated.ifPresent(mediaProducerPort::sendMediaCreatedMessage);
		
		return optMediaCreated;
	}

	@Override
	public Optional<Media> update(Long id, Media media) {
		
		Media mediaFound = mediaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID, String.valueOf(id))));
		Artist artistFound = artistRepository.findById(media.getArtist().getId()).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(media.getArtist().getId()))));
				
		mediaFound.setName(media.getName());
		mediaFound.setType(media.getType());		
		mediaFound.setArtist(artistFound);
		
		Optional<Media> optMediaUpdated = mediaRepository.save(mediaFound);
		optMediaUpdated.ifPresent(mediaProducerPort::sendMediaUpdatedMessage);
		
		return optMediaUpdated;
	}

	@Override
	public Boolean deleteById(Long id) {
		
		Media mediaFound = mediaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.MEDIAID, String.valueOf(id))));
		
		mediaRepository.deleteById(id);
		
		boolean resultValue = mediaRepository.findById(id).isEmpty();		
		if (resultValue) { mediaProducerPort.sendMediaDeletedMessage(mediaFound);}
		
		return resultValue;
	}

}
