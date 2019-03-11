package com.github.eltonsandre.discosvinil.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.SpotifyClient;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.Album;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.PageSearch;
import com.github.eltonsandre.discosvinil.api.util.MonetaryUtils;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 10 de mar de 2019 21:54:15
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InicializarDadosServiceTest {

	private static final String JSON_ROCK = "{\"albums\":{\"items\":[{\"artists\":[{\"id\":\"4UXqAaa6dQYAk18Lv7PEgX\",\"name\":\"Fall Out Boy\"}],\"id\":\"0EVJX4RlYKuApsAN5CaDa3\",\"images\":[{\"height\":64,\"url\":\"https://i.scdn.co/image/333cfc8d7c37ff5606281f2b25b4072c26cdddd2\",\"width\":64}],\"name\":\"Save Rock And Roll\",\"total_tracks\":11},{\"artists\":[{\"id\":\"3RGLhK1IP9jnYFH4BRFJBS\",\"name\":\"The Clash\"}],\"id\":\"1ZH5g1RDq3GY1OvyD0w0s2\",\"images\":[{\"height\":64,\"url\":\"https://i.scdn.co/image/cfef85df56b703d696475ad3a43cd336a3d254e2\",\"width\":64}],\"name\":\"Combat Rock\",\"total_tracks\":12},{\"artists\":[{\"id\":\"21WS9wngs9AqFckK7yYJPM\",\"name\":\"PnB Rock\"}],\"id\":\"7eFfp9r4A30ueN9A5EJbk2\",\"images\":[{\"height\":64,\"url\":\"https://i.scdn.co/image/257f72971f26af5e67a79f545ff68bdec092f560\",\"width\":64}],\"name\":\"GTTM: Goin Thru the Motions\",\"total_tracks\":14}],\"limit\":3,\"next\":\"https://api.spotify.com/v1/search?query=ROCk&type=album&offset=3&limit=3\",\"offset\":0,\"previous\":null,\"total\":38879}}";
	private static final List<Album> lista = mockAlbuns();

	@MockBean
	private SpotifyClient spotifyClient;

	@MockBean
	private InicializarDadosService inicializarDadosService;

	@Before
	public void before() {

	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.service.InicializarDadosService#gerarDados(int)}.
	 */
	@Test
	public void testGerarDados() {
		doNothing().when(this.inicializarDadosService).gerarDados(1);

		this.inicializarDadosService.gerarDados(1);

		verify(this.inicializarDadosService, times(1)).gerarDados(1);
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.service.InicializarDadosService#gerarDadosPorGenero(com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum, int)}.
	 */
	@Test
	public void testGerarDadosPorGenero() {
		when(this.spotifyClient.getAlbuns(GeneroEnum.ROCK, 3)).thenReturn(InicializarDadosServiceTest.lista);

		doNothing().when(this.inicializarDadosService).salvarDadosDeAlbums(lista, GeneroEnum.ROCK);

		doNothing().when(this.inicializarDadosService).gerarDadosPorGenero(GeneroEnum.ROCK, 3);
		this.inicializarDadosService.gerarDadosPorGenero(GeneroEnum.ROCK, 3);

		verify(this.inicializarDadosService, times(1)).gerarDadosPorGenero(GeneroEnum.ROCK, 3);
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.service.InicializarDadosService#salvarDadosDeAlbums(java.util.List, com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum)}.
	 */
	@Test
	public void testSalvarDadosDeAlbums() {
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.service.InicializarDadosService#criarDisco(com.github.eltonsandre.discosvinil.api.service.client.spotify.model.Album, com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum)}.
	 */
	@Test
	public void testCriarDisco() {
		Album album = InicializarDadosServiceTest.lista.get(0);
		when(this.inicializarDadosService.criarDisco(album, GeneroEnum.ROCK)).thenReturn(mockDisco());

		Disco criarDisco = this.inicializarDadosService.criarDisco(album, GeneroEnum.ROCK);

		assertThat(criarDisco).isNotNull();
		assertThat(criarDisco.getNome()).isEqualTo("Rock Star.");
		assertThat(criarDisco.getGenero()).isEqualTo(GeneroEnum.ROCK);
	}

	static List<Album> mockAlbuns() {
		try {
			return new ObjectMapper().readValue(JSON_ROCK, PageSearch.class).getContent().getAlbums();
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

	static Disco mockDisco() {
		Disco discoFiltro = new Disco();
		discoFiltro.setGenero(GeneroEnum.ROCK);
		discoFiltro.setNome("Rock Star.");
		discoFiltro.setValor(MonetaryUtils.gerarValorRandom());

		return discoFiltro;
	}
}
