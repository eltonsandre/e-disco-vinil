package com.github.eltonsandre.discosvinil.api.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 23:02:07
 */
@Table(name = "venda")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "data_venda", updatable = false)
	private LocalDateTime dataVenda;

	@NotEmpty(groups = { PostMapping.class }, message = "Escolha ao menos um item de venda")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItemVenda> itens;

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "total", nullable = false, precision = 10, scale = 2)
	private BigDecimal total = BigDecimal.ZERO;

	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "total_cashback", nullable = false, precision = 10, scale = 2)
	private BigDecimal totalCashback = BigDecimal.ZERO;

	public Venda(final Long id) {
		this.id = id;
	}

	@PrePersist
	private void prePersist() {
		this.dataVenda = LocalDateTime.now();
	}

	@PostPersist
	private void postPersist() {
		this.itens.forEach(item -> item.setIdVenda(new Venda(this.id)));
	}

	/**
	 * Calcula o valor total da venda
	 */
	public void calcularValorTotal() {
		this.total = this.itens.stream() // @formatter:off
				.filter(item -> item.getDisco() != null && item.getDisco().getId() != null)
				.map(item -> item.getValorTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Calcula o valor total do cashback
	 */
	public void calcularTotalCashback() {
		this.totalCashback = this.itens.stream()
				.filter(item -> item.getDisco() != null && item.getDisco().getId() != null)
				.map(item -> item.getCachback())
				.reduce(BigDecimal.ZERO, BigDecimal::add);// @formatter:on
	}
}
