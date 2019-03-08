package com.github.eltonsandre.discosvinil.api.service.client.spotify.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 19:02:59
 */
@Data
public class Album {

	private List<Artist> artists;
	private String id;

	private List<Image> images;
	private String name;

	@JsonProperty("total_tracks")
	private String totalTracks;

}
