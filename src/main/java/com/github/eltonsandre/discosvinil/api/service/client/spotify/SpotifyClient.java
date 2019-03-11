package com.github.eltonsandre.discosvinil.api.service.client.spotify;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.Album;
import com.github.eltonsandre.discosvinil.api.service.client.spotify.model.PageSearch;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 22:06:23
 */
@Service
public class SpotifyClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyClient.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RestTemplate rest;

	@Value("${com.spotify.api.oauth.accesstoken:''}")
	private String accessToken;

	@Value("${com.spotify.api.oauth.url-token:''}")
	private String urlApiAccountSpotify;

	@Value("${com.spotify.api.url-base:''}")
	private String urlBaseApiSpotify;

	@Autowired
	private Token token;

	/**
	 * <code><pre></pre></code>
	 *
	 * @param genero
	 *
	 * @return List<Album>
	 */
	public List<Album> getAlbuns(final GeneroEnum genero, final int quantidadeAlbuns) {
		if (!this.token.isValid()) {
			this.token = this.getToken();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Authorization", "Bearer " + this.token.getAccessToken());

		/* https://api.spotify.com/v1/search?query=classic&type=album&offset=0&limit=1 */// @formatter:off
		String urlBuilder = UriComponentsBuilder.fromHttpUrl(this.urlBaseApiSpotify + "/search")
				.queryParam("query", genero.toString().toLowerCase())
				.queryParam("type", "album")
				.queryParam("offset", 0)
				.queryParam("limit", quantidadeAlbuns)
				.build()
				.encode()
				.toUriString();

		LOGGER.debug("urlBuilder: {}", urlBuilder);
		try {
			ResponseEntity<PageSearch> response = this.rest.exchange(
					urlBuilder,
					HttpMethod.GET, new HttpEntity<>(headers),
					PageSearch.class);// @formatter:on new ParameterizedTypeReference<List<Albums>>() { });

			LOGGER.debug("response.getBody: {}", response.getBody());
			return response.getBody().getContent().getAlbums();

		} catch (final HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, this.messageSource
						.getMessage("messagem.spotity.erro.obter.album", null, LocaleContextHolder.getLocale()));
			}
			throw ex;
		} catch (final Exception ex) {
			LOGGER.error(" erro: {}", ex.getMessage());
		}
		return null;
	}

	/**
	 * Obtem o token de autorização na api oauth do spotify
	 *
	 * @return String access_token
	 */
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
	private Token getToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + this.accessToken);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "client_credentials");

		try {// https://api.spotify.com/v1/search
			ResponseEntity<Token> response = this.rest.postForEntity(this.urlApiAccountSpotify, new HttpEntity<>(body, headers),
					Token.class);

			MediaType contentType = response.getHeaders().getContentType();
			HttpStatus statusCode = response.getStatusCode();

			LOGGER.debug("ContentType: {} - statusCode: {}", contentType, statusCode);
			LOGGER.debug("response Body: {}", response.getBody());
			return response.getBody();

		} catch (final HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, this.messageSource
						.getMessage("messagem.spotity.erro.obter.token", null, LocaleContextHolder.getLocale()));
			}
			throw ex;
		} catch (final Exception ex) {
			LOGGER.debug("Erro ao obter o accesstoken{}", ex);
		}
		return null;
	}

}
