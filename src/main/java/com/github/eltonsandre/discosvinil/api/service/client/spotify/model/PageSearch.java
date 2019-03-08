package com.github.eltonsandre.discosvinil.api.service.client.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 01:31:31
 */
@Data
public class PageSearch {
	@JsonProperty("albums")
	private Content content;
}
