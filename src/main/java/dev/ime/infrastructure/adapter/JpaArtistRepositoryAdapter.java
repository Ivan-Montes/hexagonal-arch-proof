package dev.ime.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistRepositoryPort;
import dev.ime.infrastructure.mapper.ArtistMapper;
import dev.ime.infrastructure.repository.JpaArtistRepository;

@Repository
public class JpaArtistRepositoryAdapter implements ArtistRepositoryPort{

	private final JpaArtistRepository jpaArtistRepository;	
	private final ArtistMapper artistMapper;
	
	public JpaArtistRepositoryAdapter(JpaArtistRepository jpaArtistRepository, ArtistMapper artistMapper) {
		super();
		this.jpaArtistRepository = jpaArtistRepository;
		this.artistMapper = artistMapper;
	}

	@Override
	public List<Artist> findAll() {
		
		return artistMapper.fromListJpaToListDomain(jpaArtistRepository.findAll());
	}


	@Override
	public Optional<Artist> findById(Long id) {
		
		return jpaArtistRepository.findById(id)
				.map(artistMapper::fromJpaToDomain);
	}


	@Override
	public Optional<Artist> save(Artist artist) {
		
		return Optional.ofNullable(
				artistMapper.fromJpaToDomain(
						jpaArtistRepository.save(
								artistMapper.fromDomainToJpa(artist))));
	}


	@Override
	public void deleteById(Long id) {
		
		jpaArtistRepository.deleteById(id);
		
	}
	
}
