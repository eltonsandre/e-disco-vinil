
package com.github.eltonsandre.discosvinil.api.service.client.spotify;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 19:26:58
 */
@Data
public class Token {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenTtype;

	@JsonProperty("expires_in")
	private long expiresIn;

	private String scope;

	private LocalDateTime expireTime = LocalDateTime.now();

	public boolean isValid() {
		return this.expireTime.plus(this.expiresIn, ChronoField.MILLI_OF_DAY.getBaseUnit()).isAfter(LocalDateTime.now());
	}

}
