package dev.ime.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaRepositoryPort;
import dev.ime.infrastructure.repository.JpaMediaRepository;

@Repository
public class JpaMediaRepositoryAdapter implements MediaRepositoryPort{

	private final JpaMediaRepository jpaMediaRepository;
	private final MediaMapper mediaMapper;
	
	public JpaMediaRepositoryAdapter(JpaMediaRepository jpaMediaRepository, MediaMapper mediaMapper) {
		super();
		this.jpaMediaRepository = jpaMediaRepository;
		this.mediaMapper = mediaMapper;
	}

	@Override
	public List<Media> findAll() {
		
		return mediaMapper.fromListJpaToListDomain(jpaMediaRepository.findAll());
	}

	@Override
	public Optional<Media> findById(Long id) {
		
		return jpaMediaRepository.findById(id)
				.map(mediaMapper::fromJpaToDomain);
	}

	@Override
	public Optional<Media> save(Media media) {
		
		return Optional.ofNullable(
				mediaMapper.fromJpaToDomain(
						jpaMediaRepository.save(
								mediaMapper.fromDomainToJpa(media))));
	}

	@Override
	public void deleteById(Long id) {
		
		jpaMediaRepository.deleteById(id);
		
	}

}
