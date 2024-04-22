package dev.ime.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ime.infrastructure.entity.JpaArtistEntity;

public interface JpaArtistRepository extends JpaRepository<JpaArtistEntity, Long>{

}
