package pet.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;


//page 5.1.a
@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	//page 5.1.b
	public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
		//5.1.c
		log.error("Exception: {}", ex.toString());
		//5.1.d
		return Map.of("message", ex.toString());
	}
}
