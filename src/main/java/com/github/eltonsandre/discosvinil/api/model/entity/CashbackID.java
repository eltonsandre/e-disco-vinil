package com.github.eltonsandre.discosvinil.api.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.DiaSemanaEnum;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 21:37:04
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "diaSemana", "genero" })
public class CashbackID implements Serializable {

	/**
	 * Atributo serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo diaSemana representa o dia da semana
	 */
	@Column(name = "dia_semana", updatable = false)
	@Enumerated(EnumType.STRING)
	private DiaSemanaEnum diaSemana;

	/**
	 * Atributo genero representa o genero musical
	 */
	@Column(name = "genero", updatable = false)
	@Enumerated(EnumType.STRING)
	private GeneroEnum genero;
}
