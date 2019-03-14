package com.github.eltonsandre.discosvinil.api.resource.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton Sandré</a>
 * @date sexta-feira 26 ago 2018 20:52:12
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErroMessage {

	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();

	/**
	 * Atributo status representa "status": 500,
	 */
	@Builder.Default
	private Integer status = HttpStatus.BAD_REQUEST.value();

	/**
	 * Atributo error representa "error": "Internal Server Error",
	 */
	@Builder.Default
	private String error = HttpStatus.BAD_REQUEST.getReasonPhrase();

	/**
	 * Atributo message representa "message"
	 */
	private String message;

	/**
	 * Atributo path representa "path"
	 */
	private String path;

	@Builder.Default
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> errors = new ArrayList<>();

	public void addValidationError(final String error) {
		this.errors.add(error);
	}

	public static ErroMessage getErroMessage(final HttpStatus status, final String message, final String path) {
		ErroMessage erro = ErroMessage.builder().timestamp(LocalDateTime.now()).message(message).path(path).build();

		if (status != null) {
			erro.setError(status.getReasonPhrase());
			erro.setStatus(status.value());
		}

		return erro;
	}

}