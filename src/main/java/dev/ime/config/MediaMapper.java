package dev.ime.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.ime.domain.model.Artist;
import dev.ime.domain.model.Media;
import dev.ime.infrastructure.dto.MediaDto;
import dev.ime.infrastructure.entity.JpaMediaEntity;

@Component
public class MediaMapper {

	private ArtistMapper artistMapper;	
	
	public MediaMapper() {
		super();
	}

	public ArtistMapper getArtistMapper() {
		return artistMapper;
	}

	@Autowired
	public void setArtistMapper(@Lazy ArtistMapper artistMapper) {
		this.artistMapper = artistMapper;
	}

	public Media fromDtoToDomain(MediaDto dto) {
		
		return new Media.MediaBuilder()
				.setId(dto.mediaId())
				.setName(dto.mediaName())
				.setType(dto.mediaType())
				.setArtist(new Artist
						.ArtistBuilder()
						.setId(dto.artistId())
						.build())
				.build();				
	}
	
	public List<Media> fromListDtoToListDomain(List<MediaDto> listDto){
		
		if ( listDto == null ) {
			return new ArrayList<>();
		}
		
		return listDto.stream()
				.map(this::fromDtoToDomain)
				.toList();
	}
	
	public MediaDto fromDomainToDto(Media domain) {
		
		Long artistId = (domain.getArtist() != null && domain.getArtist().getId() != null)? domain.getArtist().getId():-1L;
		
		return new MediaDto(
				domain.getId(), 
				domain.getName(), 
				domain.getType(), 
				artistId
				);	
	}
	
	public List<MediaDto> fromListDomainToListDto(List<Media> listDomain){

		if ( listDomain == null ) {
			return new ArrayList<>();
		}
		
		return listDomain.stream()
				.map(this::fromDomainToDto)
				.toList();
	}
	
	public Media fromJpaToDomain(JpaMediaEntity jpa){
		
		return new Media.MediaBuilder()
				.setId(jpa.getId())
				.setName(jpa.getName())
				.setType(jpa.getType())
				.setArtist(artistMapper.fromJpaToDomainNoCircularDependency(jpa.getArtist()))
				.build();
	}
	
	public List<Media> fromListJpaToListDomain(List<JpaMediaEntity> listJpa){

		if ( listJpa == null ) {
			return new ArrayList<>();
		}
		
		return listJpa.stream()
			.map(this::fromJpaToDomain)
			.toList();		
	}
	
	public JpaMediaEntity fromDomainToJpa(Media domain) {
		
		JpaMediaEntity jpa = new JpaMediaEntity();
		jpa.setId(domain.getId());
		jpa.setName(domain.getName());
		jpa.setType(domain.getType());
		jpa.setArtist(artistMapper.fromDomainToJpaNoCircularDependency(domain.getArtist()));
		
		return jpa;
	}
	
	public List<JpaMediaEntity> fromListDomainToListJpa(List<Media>listDomain){
		
		if ( listDomain == null ) {
			return new ArrayList<>();
		}
		
		return listDomain.stream()
			.map(this::fromDomainToJpa)
			.toList();		
	}
	
}
