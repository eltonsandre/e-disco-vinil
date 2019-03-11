package com.github.eltonsandre.discosvinil.api.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 11 de mar de 2019 02:12:50
 */
public class MonetaryUtilsTest {

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.util.MonetaryUtils#money(java.lang.String)}.
	 */
	@Test
	public void testMoney() {
		BigDecimal money = MonetaryUtils.money("20,00");

		BigDecimal setScale = new BigDecimal(20.00).setScale(2, RoundingMode.HALF_EVEN);
		assertEquals(money, setScale);

		BigDecimal noScale = new BigDecimal(20);
		assertNotEquals(money, noScale);

		BigDecimal zeroScale2 = new BigDecimal("0.00");

		assertEquals(zeroScale2, MonetaryUtils.money(""));
		assertEquals(zeroScale2, MonetaryUtils.money(null));
		assertNotEquals(BigDecimal.ZERO, MonetaryUtils.ZERO);
	}

	/**
	 * Test method for {@link com.github.eltonsandre.discosvinil.api.util.MonetaryUtils#gerarValorRandom()}.
	 */
	@Test
	public void testGerarValorRandom() {
		BigDecimal gerarValorRandom = MonetaryUtils.gerarValorRandom();

		assertTrue(gerarValorRandom.compareTo(new BigDecimal(20)) == 1);

		assertTrue(MonetaryUtils.gerarValorRandom().compareTo(new BigDecimal(100)) == -1);
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.util.MonetaryUtils#rangeRandom(int, int)}.
	 */
	@Test
	public void testRangeRandom() {
		for (int i = 0; i < 10; i++) {
			int rangeRandom = MonetaryUtils.rangeRandom(1, 10);
			assertTrue(rangeRandom > 0 && rangeRandom < 11);
		}
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.util.MonetaryUtils#currencyFormat(java.math.BigDecimal)}.
	 */
	@Test
	public void testCurrencyFormat() {
		assertTrue("R$20,00".equals(MonetaryUtils.currencyFormat(new BigDecimal(20.00))));
	}

}
