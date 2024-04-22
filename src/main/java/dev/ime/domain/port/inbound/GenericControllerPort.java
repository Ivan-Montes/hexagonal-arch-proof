package dev.ime.domain.port.inbound;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface GenericControllerPort<T> {

	ResponseEntity<List<T>> getAll();
	ResponseEntity<T>getById(Long id);
	ResponseEntity<T>create(T dto);
	ResponseEntity<T>update(Long id, T dto);
	ResponseEntity<Boolean>deleteById(Long id);
}
