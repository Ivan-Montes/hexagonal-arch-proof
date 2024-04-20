package dev.ime.application.error;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.ime.application.exception.BasicException;



@ControllerAdvice
public class ApplicationLevelExceptionHandler {

	private final Logger logger;	
	
	public ApplicationLevelExceptionHandler(Logger logger) {
		super();
		this.logger = logger;
	}

	@ExceptionHandler({
		dev.ime.application.exception.ResourceNotFoundException.class,
		dev.ime.application.exception.EntityAssociatedException.class,
		dev.ime.application.exception.BasicException.class
		})
	public ResponseEntity<ExceptionResponse> basicException(BasicException ex){
	
		logger.severe("Threw " + ex.getName() + " :=: An implementation of Basic Exception Class");
		return new ResponseEntity<>( new ExceptionResponse( ex.getIdentifier(),ex.getName(),ex.getDescription(),ex.getErrors() ),
									HttpStatus.NOT_FOUND );
	}
	
	
}
