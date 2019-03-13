package com.github.eltonsandre.discosvinil.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 10 de mar de 2019 21:06:55
 */
public abstract class MonetaryUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonetaryUtils.class);

	public static final BigDecimal ZERO = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN);

	/**
	 * @return BigDecimal
	 */
	public static BigDecimal money(final String value) {
		if (StringUtils.isBlank(value)) {
			return ZERO;
		}
		BigDecimal valor = new BigDecimal(value.replace(",", ".")).setScale(2, RoundingMode.HALF_EVEN);
		LOGGER.debug("valor: {}", NumberFormat.getCurrencyInstance().format(valor));
		return valor;
	}

	/**
	 * @return BigDecimal
	 */
	public static BigDecimal gerarValorRandom() {
		BigDecimal valor = new BigDecimal(rangeRandom(20, 50) + "." + rangeRandom(0, 99)).setScale(2,
				RoundingMode.HALF_EVEN);
		LOGGER.debug("valor: {}", NumberFormat.getCurrencyInstance().format(valor));
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

}
