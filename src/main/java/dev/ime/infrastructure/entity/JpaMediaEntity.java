package dev.ime.infrastructure.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medias")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JpaMediaEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "media_id" )
	private Long id;
	
	@Column( nullable = false, length = 50)
	private String name;
	
	@Column( nullable = false, length = 50)
	private String type;

	@ManyToOne
	@JoinColumn( name = "artist_id", nullable = false)
	@ToString.Exclude
	private JpaArtistEntity artist;

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaMediaEntity other = (JpaMediaEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}
	
	
}
