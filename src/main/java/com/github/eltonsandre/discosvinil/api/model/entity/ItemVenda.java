package com.github.eltonsandre.discosvinil.api.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 22:07:17
 */
@Entity
@Table(name = "item_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class ItemVenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 3)
	private Integer quantidade = 1;

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	@Column(name = "cachback", nullable = false, precision = 10, scale = 2)
	private BigDecimal cachback = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "id_disco", nullable = false)
	private Disco disco;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_venda", nullable = false)
	private Venda venda;

	@Transient
	public BigDecimal getValorTotal() {
		return this.valorUnitario.multiply(new BigDecimal(this.quantidade));
	}
}
