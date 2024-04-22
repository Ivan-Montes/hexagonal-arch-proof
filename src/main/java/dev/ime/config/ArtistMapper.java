package dev.ime.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.ime.domain.model.Artist;
import dev.ime.infrastructure.dto.ArtistDto;
import dev.ime.infrastructure.entity.JpaArtistEntity;


@Component
public class ArtistMapper {

	private MediaMapper mediaMapper;	
	
	public ArtistMapper() {
		super();
	}		

	public MediaMapper getMediaMapper() {
		return mediaMapper;
	}
	
	@Autowired
	public void setMediaMapper(@Lazy MediaMapper mediaMapper) {
		this.mediaMapper = mediaMapper;
	}

	public Artist fromDtoToDomain(ArtistDto dto) {
		
		return new Artist.ArtistBuilder()
				.setId(dto.artistId())
				.setName(dto.artistName())
				.setSurname(dto.artistSurname())
				.setArtisticName(dto.artisticName())
				.setMedias(mediaMapper.fromListDtoToListDomain(dto.medias()))
				.build();
	}	
		
	public ArtistDto fromDomainToDto(Artist domain) {
		
		return new ArtistDto(
				domain.getId(),
				domain.getName(),
				domain.getSurname(),
				domain.getArtisticName(),
				mediaMapper.fromListDomainToListDto(domain.getMedias())
				);	
	}
	
	public List<ArtistDto> fromListDomainToListDto(List<Artist>listDomain){

		if ( listDomain == null ) {
			return new ArrayList<>();
		}
		
		return listDomain.stream()
				.map(this::fromDomainToDto)
				.toList();
	}
	
//
	public Artist fromJpaToDomain(JpaArtistEntity jpa){
		
		return new Artist.ArtistBuilder()
				.setId(jpa.getId())
				.setName(jpa.getName())
				.setSurname(jpa.getSurname())
				.setArtisticName(jpa.getArtisticName())
				.setMedias(mediaMapper.fromListJpaToListDomain(jpa.getMedias()))
				.build();
	}
	
	public Artist fromJpaToDomainNoCircularDependency(JpaArtistEntity jpa){
		
		return new Artist.ArtistBuilder()
				.setId(jpa.getId())
				.setName(jpa.getName())
				.setSurname(jpa.getSurname())
				.setArtisticName(jpa.getArtisticName())
				.build();
	}
	
	public List<Artist> fromListJpaToListDomain(List<JpaArtistEntity> listJpa){

		if ( listJpa == null ) {
			return new ArrayList<>();
		}
		
		return listJpa.stream()
			.map(this::fromJpaToDomain)
			.toList();		
	}	

	public JpaArtistEntity fromDomainToJpa(Artist domain) {
		
		JpaArtistEntity jpa = new JpaArtistEntity();
		jpa.setId(domain.getId());
		jpa.setName(domain.getName());
		jpa.setSurname(domain.getSurname());
		jpa.setArtisticName(domain.getArtisticName());
		jpa.setMedias(mediaMapper.fromListDomainToListJpa(domain.getMedias()));
		
		return jpa;
	}

	public JpaArtistEntity fromDomainToJpaNoCircularDependency(Artist domain) {
		
		JpaArtistEntity jpa = new JpaArtistEntity();
		jpa.setId(domain.getId());
		jpa.setName(domain.getName());
		jpa.setSurname(domain.getSurname());
		jpa.setArtisticName(domain.getArtisticName());
		
		return jpa;
	}
	
}
