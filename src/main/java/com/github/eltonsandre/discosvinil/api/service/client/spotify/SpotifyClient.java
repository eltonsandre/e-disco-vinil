package com.github.eltonsandre.discosvinil.api.service.client.spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.github.eltonsandre.discosvinil.api.repository.entity.enunn.GeneroEnum;
import com.wrapper.spotify.model_objects.specification.Paging;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 22:06:23
 */
@Service
public class SpotifyClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyClient.class);

	@Value("${com.spotify.oauth.accesstoken:''}")
	private String accessToken;

	public Paging<Album> searchAlbums(final GeneroEnum genero, final Integer limit) {
		try {

			new RestTemplate();

			// LOGGER.debug("Total: {}", albuns.getTotal());
			return null;
		} catch (Exception e) {
			LOGGER.error("Error: {}", e.getMessage());
		}
		return null;
	}

	private void getToken() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestHeaders.add("Authorization", "Basic " + this.accessToken);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + this.accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		// body("grant_type","client_credentials");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

	}

}
