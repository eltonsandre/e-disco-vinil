
package com.github.eltonsandre.discosvinil.api.resource.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

 
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponseImpl<T> extends PageImpl<T> {
	/**
	 * Atributo serialVersionUID representa
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor
	 */
	public PageResponseImpl() {
		super(new ArrayList<>());
	}

	/**
	 * Construtor
	 *
	 * @param content
	 */
	public PageResponseImpl(final List<T> content) {
		super(content);
	}

	/**
	 * Construtor
	 *
	 * @param content
	 * @param pageable
	 * @param total
	 */
	public PageResponseImpl(final List<T> content, final Pageable pageable, final long total) {
		super(content, pageable, total);
	}

	/**
	 * Construtor
	 *
	 * @param content
	 * @param number
	 * @param size
	 * @param totalElements
	 * @param pageable
	 * @param last
	 * @param totalPages
	 * @param sort
	 * @param first
	 * @param numberOfElements
	 */
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public PageResponseImpl(// @formatter:off
		@JsonProperty("content") final List<T> content,
        @JsonProperty("number") final int number,
        @JsonProperty("size") final int size,
        @JsonProperty("totalElements") final Long totalElements,
        @JsonProperty("pageable") final JsonNode pageable,
        @JsonProperty("last") final boolean last,
        @JsonProperty("totalPages") final int totalPages,
        @JsonProperty("sort") final JsonNode sort,
        @JsonProperty("first") final boolean first,
        @JsonProperty("numberOfElements") final int numberOfElements
        ) {// @formatter:on
		super(content, PageRequest.of(number, size), totalElements);
	}

}