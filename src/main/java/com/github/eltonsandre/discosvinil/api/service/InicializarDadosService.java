package com.github.eltonsandre.discosvinil.api.service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;
import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.SpotifyClient;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.Album;
import com.github.eltonsandre.discosvinil.api.util.MonetaryUtils;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 22:26:37
 */
@Service
public class InicializarDadosService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InicializarDadosService.class);

	@Autowired
	private SpotifyClient spotifyApi;

	@Autowired
	private CatalogoRepository catalogoRepository;

	public void gerarDados(final Integer quantidadeAlbuns) {
		LOGGER.info("Executando serviço de geração de massa inicial usando API Spotify...");
		try {
			EnumSet.allOf(GeneroEnum.class).forEach(genero -> {
				LOGGER.debug("enum: {} {} ", genero);
				this.gerarDadosPorGenero(genero, quantidadeAlbuns);
			});
		} catch (Exception e) {
			LOGGER.error("Error: {}", e.getMessage());
		}
		LOGGER.info("Geração de massa inicial Finalizada.");
	}

	public void gerarDadosPorGenero(@NotNull final GeneroEnum genero, final int quantidadeAlbuns) {
		LOGGER.debug("Discos do Genero: {}", genero);
		List<Album> albums = this.spotifyApi.getAlbuns(genero, quantidadeAlbuns);
		this.salvarDadosDeAlbums(albums, genero);
	}

	/**
	 * @param albums
	 * @param genero void
	 */
	public void salvarDadosDeAlbums(@NotNull final List<Album> albums, final GeneroEnum genero) {
		List<Disco> discos = albums.stream().map(album -> this.criarDisco(album, genero)).collect(Collectors.toList());

		List<Disco> saveAll = this.catalogoRepository.saveAll(discos);
		LOGGER.debug("{} discos do Genero {} foram salvo!", saveAll.size(), genero);
	}

	/**
	 * @param album
	 * @param genero
	 * @return Disco
	 */
	public Disco criarDisco(final Album album, final GeneroEnum genero) {
		Disco disco = new Disco();
		disco.setGenero(genero);
		disco.setNome(album.getName());
		disco.setValor(MonetaryUtils.gerarValorRandom());

		try {
			disco.setIdArtista(album.getArtists().get(0).getId());
			disco.setArtista(album.getArtists().get(0).getName());
			disco.setImagem(album.getImages().get(0).getUrl());
		} catch (Exception e) {
			LOGGER.error("Erro acessar alguma lista");
		}

		disco.setIdSpotify(album.getId());
		return disco;
	}

}
