package com.github.eltonsandre.discosvinil.api.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 21:30:51
 */
@Table(name = "cashback")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@AllArgsConstructor
public class Cashback {

	@EmbeddedId
	private CashbackPK id;

	@Column(name = "porcentagem", nullable = false)
	private BigDecimal porcentagem;

}
