package com.github.eltonsandre.discosvinil.api.resource.handler;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.eltonsandre.discosvinil.api.resource.exception.ApiRestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">eltonsandre</a>
 * @date 13 de mar de 2019
 */
@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		String message = this.messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());

		ErroMessage erro = ErroMessage.getErroMessage(HttpStatus.BAD_REQUEST, message,
				StringUtils.substringBetween(request.getDescription(true), "uri=", ";"));
		erro.addValidationError(ex.getCause().getMessage());

		return this.handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		ErroMessage error = this.fromBindingErrors(exception.getBindingResult());
		error.setPath(StringUtils.substringBetween(request.getDescription(true), "uri=", ";"));

		return super.handleExceptionInternal(exception, error, headers, status, request);
	}

	/**
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ Exception.class, HttpClientErrorException.class })
	public ResponseEntity<Object> handleHttpClientErrorException(final Exception ex, final WebRequest request) {
		String message;
		try {
			message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			message = ex.getMessage();
		}

		ErroMessage erro = ErroMessage.getErroMessage(HttpStatus.BAD_REQUEST, message, request.getContextPath());
		return this.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * <code><pre>EmptyResultDataAccessException</pre></code>
	 *
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex,
			final WebRequest request) {
		String message = this.messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());

		ErroMessage erro = ErroMessage.getErroMessage(HttpStatus.NOT_FOUND, message, request.getContextPath());
		return this.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * <code><pre>DataIntegrityViolationException</pre></code>
	 *
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex,
			final WebRequest request) {
		String message = this.messageSource.getMessage("recurso.operacao-nao-permitida", null,
				LocaleContextHolder.getLocale());

		ErroMessage erro = ErroMessage.getErroMessage(HttpStatus.BAD_REQUEST, message, request.getContextPath());
		return this.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * <code><pre>DataIntegrityViolationException</pre></code>
	 *
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
			final WebRequest request) {
		String message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());

		ErroMessage erro = ErroMessage.getErroMessage(HttpStatus.BAD_REQUEST, message,
				StringUtils.substringBetween(request.getDescription(true), "uri=", ";"));
		return this.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ ApiRestClientException.class })
	public <E extends ApiRestClientException> ResponseEntity<Object> handleApiSExceptionDefault(final E ex,
			final WebRequest request) {
		String message;
		try {
			String[] split1 = StringUtils.split(ex.getMessage(), ";");
			message = this.messageSource.getMessage(split1[0], null, LocaleContextHolder.getLocale());
		} catch (org.springframework.context.NoSuchMessageException e) {
			message = ex.getMessage();
		}
		HttpStatus httpStatus = ex.getHttpStatus() == null ? HttpStatus.CONFLICT : ex.getHttpStatus();

		ErroMessage erro = ErroMessage.getErroMessage(httpStatus, message,
				StringUtils.substringBetween(request.getDescription(true), "uri=", ";"));

		return this.handleExceptionInternal(ex, erro, new HttpHeaders(), httpStatus, request);
	}

	/**
	 * @param errors
	 * @return ValidationError
	 */
	public ErroMessage fromBindingErrors(final Errors errors) {

		final Object[] errorsObj = new Object[] { errors.getErrorCount() };
		final String messages = this.messageSource.getMessage("recurso.falha.validacao", errorsObj,
				LocaleContextHolder.getLocale());

		ErroMessage error = ErroMessage.builder().message(messages).build();

		for (FieldError objectError : errors.getFieldErrors()) {
			log.debug("DefaultMessage: {} - Field: {} ", objectError.getDefaultMessage(), objectError.getField());
			final Object[] obj = new Object[] { objectError.getField() };
			final String message = MessageFormat.format(objectError.getDefaultMessage(), obj);
			error.addValidationError(message);
		}

		return error;
	}

}
