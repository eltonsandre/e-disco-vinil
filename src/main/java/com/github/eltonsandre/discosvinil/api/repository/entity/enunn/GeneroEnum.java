package com.github.eltonsandre.discosvinil.api.repository.entity.enunn;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 21:33:02
 */
/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 01:23:07
 */
@Getter
@AllArgsConstructor
public enum GeneroEnum {

	POP(1, "pop"), //
	MPB(2, "mpb"), //
	CLASSIC(3, "classic"), //
	ROCK(4, "rock");

	public static final Map<String, GeneroEnum> MAP;

	static {
		MAP = new HashMap<>();
		EnumSet.allOf(GeneroEnum.class).forEach(day -> MAP.put(day.descricao, day));
	}

	private int id;
	private String descricao;

	public static GeneroEnum keyOf(final String descricao) {
		return MAP.get(descricao);
	}
}
