package com.cleverit.testdevelop.exceptions;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintViolationException;

import com.cleverit.testdevelop.models.endpoints.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		String message;
		ErrorResponse errorResponse;
		StringBuilder errors = new StringBuilder();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		for (FieldError fieldError : fieldErrors) {
			message = fieldError.getDefaultMessage();
			errors.append(message).append(". ");
		}

		errorResponse = new ErrorResponse(errors.toString());

		return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler({ResponseStatusException.class})
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
		String message = Objects.requireNonNull(ex.getLocalizedMessage()).split("\"")[1];
		ErrorResponse errorResponse = new ErrorResponse(message);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), ex.getStatus());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
