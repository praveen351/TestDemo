package com.devops.capstone.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}
	// @ExceptionHandler(ResourceNotFoundException.class)
	// public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException
	// ex, WebRequest request) {
	// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	// request.getDescription(false));
	// return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	// }

	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<?>
	// globleExcpetionHandler(MethodArgumentNotValidException ex, HttpHeaders
	// headers,
	// HttpStatus status, Exception exeption, WebRequest request) {
	//
	// List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x
	// -> x.getDefaultMessage())
	// .collect(Collectors.toList());
	//
	// ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(),
	// "");
	// return new ResponseEntity<>(errorDetails, headers, status);
	// }
}
