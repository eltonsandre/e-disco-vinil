package com.github.eltonsandre.discosvinil.api.model.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 21:33:14
 */
@Getter
@AllArgsConstructor
public enum DiaSemanaEnum {
	// @formatter:off
	SEGUNDA(1, "segunda"),
	TERCA(2, "terca"),
	QUARTA(3, "quarta"),
	QUINTA(4, "quinta"),
	SEXTA(5, "sexta"),
	SABADO(6, "sabado"),
	DOMINGO(7, "domingo");
	// @formatter:on

	private int id;
	private String descricao;
}
