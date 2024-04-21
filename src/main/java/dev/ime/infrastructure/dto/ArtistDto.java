package dev.ime.infrastructure.dto;

import java.util.List;

import dev.ime.infrastructure.config.InfrastructureConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record ArtistDto(
		Long artistId,
		@NotEmpty @Size( min = 1, max = 50 ) String artistName,
		@NotEmpty @Size( min = 1, max = 50 ) String artistSurname,
		@NotEmpty @Size( min = 1, max = 50 ) String artisticName,
		List<MediaDto>medias) {
	
	public ArtistDto() {
		this(0L, InfrastructureConstant.NODATA, "", "", null);
	}
	
}
