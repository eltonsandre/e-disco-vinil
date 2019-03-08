package com.github.eltonsandre.discosvinil.api.service.client.spotify.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 8 de mar de 2019 06:28:01
 */
@Data
public class Content {
	@JsonProperty("items")
	private List<Album> albums;

	private Integer limit;
	private String next;
	private Integer offset;
	private Integer previous;
	private Integer total;
}