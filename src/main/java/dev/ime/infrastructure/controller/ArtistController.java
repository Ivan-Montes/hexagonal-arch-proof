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

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistPort;
import dev.ime.domain.port.inbound.GenericControllerPort;
import dev.ime.infrastructure.dto.ArtistDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/artists")
@Tag(name = "Artist", description="Artist Operations")
public class ArtistController implements GenericControllerPort<ArtistDto>{

	private final ArtistPort artistPort;
	private final ArtistMapper artistMapper;
	
	public ArtistController(ArtistPort artistPort, ArtistMapper artistMapper) {
		super();
		this.artistPort = artistPort;
		this.artistMapper = artistMapper;
	}

	@GetMapping
	@Override
	@Operation(summary="Get a List of all Artist", description="Get a List of all Artist, @return an object Response with a List of DTO's")
	public ResponseEntity<List<ArtistDto>> getAll() {
		
		List<Artist>list = artistPort.getAll();		
		
		return ResponseEntity.ok( !list.isEmpty()? artistMapper.fromListDomainToListDto(list):Collections.emptyList());
	}

	@GetMapping("/{id}")
	@Override
	@Operation(summary="Get a Artist according to an Id", description="Get a Artist according to an Id, @return an object Response with the entity required in a DTO")
	public ResponseEntity<ArtistDto> getById(@PathVariable Long id) {
		
		Optional<Artist> optArtist = artistPort.getById(id);
		
		return ResponseEntity.ok(optArtist.map(artistMapper::fromDomainToDto).orElse(new ArtistDto()));
	}

	@PostMapping
	@Override
	@Operation(summary="Create a new Artist", description="Create a new Artist, @return an object Response with the entity in a DTO")
	public ResponseEntity<ArtistDto> create(@Valid @RequestBody ArtistDto dto) {
		
		Artist artist = artistMapper.fromDtoToDomain(dto);
		Optional<Artist> optArtistCreated =  artistPort.create(artist);
		
		return optArtistCreated.isPresent()? new ResponseEntity<>(artistMapper.fromDomainToDto(optArtistCreated.get()),HttpStatus.CREATED)
				:new ResponseEntity<>(new ArtistDto(), HttpStatus.OK);								
	}

	@PutMapping("/{id}")
	@Override
	@Operation(summary="Update fields in a Artist", description="Update fields in a Artist, @return an object Response with the entity modified in a DTO")
	public ResponseEntity<ArtistDto> update(@PathVariable Long id, @Valid @RequestBody ArtistDto dto) {
		
		Artist artist = artistMapper.fromDtoToDomain(dto);
		Optional<Artist> optArtistUpdated =  artistPort.update(id, artist);
		
		return ResponseEntity.ok(optArtistUpdated.map(artistMapper::fromDomainToDto).orElse(new ArtistDto()));
		
	}

	@DeleteMapping("/{id}")
	@Override
	@Operation(summary="Delete a Artist by its Id", description="Delete a Artist by its Id, @return an object Response with a message")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
		
		return ResponseEntity.ok( artistPort.deleteById(id) );
	}

}
