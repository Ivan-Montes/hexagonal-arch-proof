package dev.ime.domain.model;


public class Media {

	private Long id;
	private String name;
	private String type;
	private Artist artist;
	
	private Media(Media.MediaBuilder mediaBuilder) {
		
		this.id = mediaBuilder.id;
		this.name = mediaBuilder.name;
		this.type = mediaBuilder.type;
		this.artist = mediaBuilder.artist;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", name=" + name + ", type=" + type + ", Artist=" + artist + "]";
	}
	
	public static class MediaBuilder{
		
		private Long id;
		private String name;
		private String type;
		private Artist artist;
		
		public MediaBuilder() {
			super();
		}

		public MediaBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public MediaBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public MediaBuilder setType(String type) {
			this.type = type;
			return this;
		}

		public MediaBuilder setArtist(Artist artist) {
			this.artist = artist;
			return this;
		}
		
		public Media build() {
			return new Media(this);
		}
		
	}
	
}
