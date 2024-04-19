package dev.ime.domain.model;

import java.util.ArrayList;
import java.util.List;


public class Artist {

	private Long id;
	private String name;
	private String surname;
	private String artisticName;
	private List<Media>medias = new ArrayList<>();
	
	private Artist(Artist.ArtistBuilder artistBuilder) {
		
		this.id = artistBuilder.id;
		this.name = artistBuilder.name;
		this.surname = artistBuilder.surname;
		this.artisticName = artistBuilder.artisticName;
		this.medias = artistBuilder.medias;
		
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getArtisticName() {
		return artisticName;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setArtisticName(String artisticName) {
		this.artisticName = artisticName;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", surname=" + surname + ", artisticName=" + artisticName + "]";
	}
	
	public static class ArtistBuilder {		

		private Long id;
		private String name;
		private String surname;
		private String artisticName;
		private List<Media>medias = new ArrayList<>();
		
		public ArtistBuilder(){
			super();
		}

		public ArtistBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ArtistBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ArtistBuilder setSurname(String surname) {
			this.surname = surname;
			return this;
		}

		public ArtistBuilder setArtisticName(String artisticName) {
			this.artisticName = artisticName;
			return this;
		}
		
		public ArtistBuilder setMedias(List<Media> medias) {
			this.medias = medias;
			return this;
		}

		public Artist build() {
			return new Artist(this);
		}
		
	}
	
}
