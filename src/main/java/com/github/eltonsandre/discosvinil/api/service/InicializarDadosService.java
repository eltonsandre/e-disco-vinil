package com.github.eltonsandre.discosvinil.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.service.client.spotify.SpotifyClient;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 22:26:37
 */
@Service
public class InicializarDadosService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InicializarDadosService.class);

	@Autowired
	private SpotifyClient spotifyApi;

	public void gerarMassaDadosAlbum() {// TODO
		// List<Album> albuns = this.spotifyApi.searchAlbums(GeneroEnum.CLASSIC, 50);
		// albuns.getItems();
	}

}
