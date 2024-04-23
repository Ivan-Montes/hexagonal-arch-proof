package dev.ime.infrastructure.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.GenericControllerPort;
import dev.ime.domain.port.inbound.MediaPort;
import dev.ime.infrastructure.dto.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medias")
@Tag(name = "Media", description="Media Operations")
public class MediaController implements GenericControllerPort<MediaDto>{

	private final MediaPort mediaPort;
	private final MediaMapper mediaMapper;
	
	public MediaController(MediaPort mediaPort, MediaMapper mediaMapper) {
		super();
		this.mediaPort = mediaPort;
		this.mediaMapper = mediaMapper;
	}

	@GetMapping
	@Override
	@Operation(summary="Get a List of all Media", description="Get a List of all Media, @return an object Response with a List of DTO's")
	public ResponseEntity<List<MediaDto>> getAll() {
		
		List<Media>list = mediaPort.getAll();		
		
		return ResponseEntity.ok( !list.isEmpty()? mediaMapper.fromListDomainToListDto(list):Collections.emptyList());
	}

	@GetMapping("/{id}")
	@Override
	@Operation(summary="Get a Media according to an Id", description="Get a Media according to an Id, @return an object Response with the entity required in a DTO")
	public ResponseEntity<MediaDto> getById(@PathVariable Long id) {
		
		Optional<Media>optMedia = mediaPort.getById(id);
		
		return ResponseEntity.ok(optMedia.map(mediaMapper::fromDomainToDto).orElse(new MediaDto()));
	}

	@PostMapping
	@Override
	@Operation(summary="Create a new Media", description="Create a new Media, @return an object Response with the entity in a DTO")
	public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaDto dto) {
		
		Media media = mediaMapper.fromDtoToDomain(dto);
		Optional<Media>optMediaCreated = mediaPort.create(media);
		
		return optMediaCreated.isPresent()? new ResponseEntity<>(mediaMapper.fromDomainToDto(optMediaCreated.get()), HttpStatus.CREATED)
				:new ResponseEntity<>(new MediaDto(), HttpStatus.OK);	
	}

	@PutMapping("/{id}")
	@Override
	@Operation(summary="Update fields in a Media", description="Update fields in a Media, @return an object Response with the entity modified in a DTO")
	public ResponseEntity<MediaDto> update(@PathVariable Long id, @Valid @RequestBody MediaDto dto) {
		
		Media media = mediaMapper.fromDtoToDomain(dto);
		Optional<Media>optMediaUpdated = mediaPort.update(id, media);
		
		return ResponseEntity.ok(optMediaUpdated.map(mediaMapper::fromDomainToDto).orElse(new MediaDto()));
	}

	@DeleteMapping("/{id}")
	@Override
	@Operation(summary="Delete a Media by its Id", description="Delete a Media by its Id, @return an object Response with a message")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
		
		return ResponseEntity.ok( mediaPort.deleteById(id) );
				
	}

}
