package dev.ime.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ime.infrastructure.entity.JpaMediaEntity;

public interface JpaMediaRepository extends JpaRepository<JpaMediaEntity, Long>{

}
