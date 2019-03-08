package com.github.eltonsandre.discosvinil.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository;
import com.github.eltonsandre.discosvinil.api.repository.entity.Disco;
import com.github.eltonsandre.discosvinil.api.repository.entity.enunn.GeneroEnum;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.SpotifyClient;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.Album;

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
	private CatalogoService catalogoService;

	@Autowired
	private CatalogoRepository catalogoRepository;

	Currency real = Currency.getInstance("BRL");

	public void gerarDados(final int quantidadeAlbuns) {
		try {
			GeneroEnum.MAP.entrySet().stream().forEach(e -> {
				LOGGER.debug("enum: {} {} ", e, e.getValue());
				this.gerarDadosPorGenero(e.getValue(), quantidadeAlbuns);
			});
		} catch (Exception e) {
			LOGGER.error("Error: {}", e.getMessage());
		}
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
		disco.setValor(gerarValorDoDisco());

		try {
			disco.setIdArtista(album.getArtists().get(0).getName());
			disco.setArtista(album.getArtists().get(0).getName());
			disco.setImagem(album.getImages().get(0).getUrl());
		} catch (Exception e) {
			LOGGER.error("Erro acessar alguma lista");
		}

		disco.setIdSpotify(album.getId());
		return disco;
	}

	/**
	 * @return BigDecimal
	 */
	public static BigDecimal gerarValorDoDisco() {
		int decimal = (int) (20 + Math.random() * (50 - 20 + 1));
		int centavos = (int) (1 + Math.random() * (99 - 1 + 1));

		valorStr.replace(",", ".");
		BigDecimal valor = new BigDecimal(decimal + "," + centavos).setScale(2, RoundingMode.HALF_EVEN);
		LOGGER.debug("valor: {}", currencyFormat(valor));
		return valor;
	}

	/**
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int rangeRandom(final int min, final int max) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}

	/**
	 * @param valor
	 * @return String
	 */
	public static String currencyFormat(final BigDecimal valor) {
		// new java.text.DecimalFormat("¤ #,###,##0.00");
		return NumberFormat.getCurrencyInstance().format(valor);
	}

}
