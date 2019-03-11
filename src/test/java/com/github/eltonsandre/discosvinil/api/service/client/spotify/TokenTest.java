package com.github.eltonsandre.discosvinil.api.service.client.spotify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 11 de mar de 2019 03:36:48
 */
public class TokenTest {

	/**
	 * Test method for {@link com.github.eltonsandre.discosvinil.api.service.client.spotify.Token#isValid()}.
	 */
	@Test
	public void testIsValid_returnTrue() {
		Token token = new Token();
		token.setExpiresIn(3600);

		assertTrue(token.isValid());
	}

	@Test
	public void testIsValid_returnFalse() {
		Token token = new Token();
		token.setExpiresIn(-3600);

		assertFalse(token.isValid());
	}

}
