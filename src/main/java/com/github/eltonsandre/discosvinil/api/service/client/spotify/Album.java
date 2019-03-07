package com.github.eltonsandre.discosvinil.api.service.client.spotify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.enums.AlbumType;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.ExternalUrl;
import com.wrapper.spotify.model_objects.specification.Image;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 01:31:31
 */
@Data
public class Album {

	private final AlbumType albumType;
	private final ArtistSimplified[] artists;
	private final CountryCode[] availableMarkets;
	private final ExternalUrl externalUrls;
	private final String href;
	private final String id;
	private final Image[] images;
	private final String name;
	private final ModelObjectType type;
	private final String uri;
}
