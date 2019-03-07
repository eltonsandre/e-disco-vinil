package com.github.eltonsandre.discosvinil.api.repository.entity.enunn;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 21:33:14
 */
@Getter
@AllArgsConstructor
public enum DiaSemanaEnum {

	DOMINGO(1, "domingo"),// @formatter:off
	SEGUNDA(2, "segunda"),
	TERCA(3, "terca"),
	QUARTA(4, "quarta"),
	QUINTA(5, "quinta"),
	SEXTA(6, "sexta"),
	SABADO(7, "sabado");// @formatter:on

	private int id;
	private String descricao;
}
