package dev.ime.infrastructure.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "artists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JpaArtistEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "artist_id" )
	private Long id;

	@Column( nullable = false, length = 50)
	private String name;

	@Column( nullable = false, length = 50)
	private String surname;

	@Column( name = "artistic_name", nullable = false, length = 50)
	private String artisticName;
	
	@Column( nullable = false)
	@OneToMany( mappedBy = "artist")
	@ToString.Exclude
	private List<JpaMediaEntity>medias = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(artisticName, id, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaArtistEntity other = (JpaArtistEntity) obj;
		return Objects.equals(artisticName, other.artisticName) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}	
	
}
