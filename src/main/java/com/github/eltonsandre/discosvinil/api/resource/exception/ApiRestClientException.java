package com.github.eltonsandre.discosvinil.api.resource.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 21:44:37
 */
public class ApiRestClientException extends RuntimeException {

	/**
	 * Atributo serialVersionUID representa
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private HttpStatus httpStatus;

	/**
	 * Construtor
	 */
	public ApiRestClientException(final HttpStatus httpStatus, final String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	/**
	 * Construtor
	 */
	public ApiRestClientException(final HttpStatus httpStatus, final String message, final Throwable throwable) {
		super(message, throwable);
		this.httpStatus = httpStatus;
	}

}
