package dev.ime.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.application.config.ApplicationConstant;
import dev.ime.application.exception.EntityAssociatedException;
import dev.ime.application.exception.ResourceNotFoundException;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistPort;
import dev.ime.domain.port.outbound.ArtistProducerPort;
import dev.ime.domain.port.outbound.ArtistRepositoryPort;


@Service
public class ArtistService implements ArtistPort{

	private final ArtistRepositoryPort artistRepository;
	private final ArtistProducerPort artistProducerPort;
	
	public ArtistService(ArtistRepositoryPort artistRepository, ArtistProducerPort artistProducertPort) {
		super();
		this.artistRepository = artistRepository;
		this.artistProducerPort = artistProducertPort;
	}

	@Override
	public List<Artist> getAll() {
		
		return artistRepository.findAll();		
	}

	@Override
	public Optional<Artist> getById(Long id) {
		
		return Optional.ofNullable( artistRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(id)))));
	}

	@Override
	public Optional<Artist> create(Artist artist) {
		
		Optional<Artist> optArtistCreated = artistRepository.save(artist);
		optArtistCreated.ifPresent(artistProducerPort::sendArtistCreatedMessage);
		
		return optArtistCreated;
	}

	@Override
	public Optional<Artist> update(Long id, Artist artist) {
		
		Artist artistFound = artistRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(id))));

		artistFound.setName(artist.getName());
		artistFound.setSurname(artist.getSurname());
		artistFound.setArtisticName(artist.getArtisticName());
		
		Optional<Artist> optArtistUpdated = artistRepository.save(artistFound);
		optArtistUpdated.ifPresent(artistProducerPort::sendArtistUpdatedMessage);

		return optArtistUpdated;
	}

	@Override
	public Boolean deleteById(Long id) {
		
		Artist artistFound = artistRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(Map.of(ApplicationConstant.ARTISTID, String.valueOf(id))));

		if (artistFound.getMedias().isEmpty()) {

			artistRepository.deleteById(id);
			
			boolean resultValue = artistRepository.findById(id).isEmpty();
			if (resultValue) { artistProducerPort.sendArtistDeletedMessage(artistFound);}
			
			return resultValue;
		}
		
		throw new EntityAssociatedException( Map.of( ApplicationConstant.ARTISTID, String.valueOf(id) ) );
	}
	
}
