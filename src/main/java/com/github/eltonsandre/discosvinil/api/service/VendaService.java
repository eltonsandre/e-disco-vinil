package com.github.eltonsandre.discosvinil.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.model.entity.CashbackID;
import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.ItemVenda;
import com.github.eltonsandre.discosvinil.api.model.entity.Venda;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.DiaSemanaEnum;
import com.github.eltonsandre.discosvinil.api.repository.CashbackRepository;
import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository;
import com.github.eltonsandre.discosvinil.api.repository.VendaRepository;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 23:15:04
 */
@Service

public class VendaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendaService.class);

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private CashbackRepository cashbackRepository;

	@Autowired
	private VendaRepository vendaRepository;

	/**
	 * <code><pre></pre></code>
	 *
	 * @param venda
	 * @param pageable
	 * @return Venda
	 */
	public Venda salvar(final Venda venda, final Pageable pageable) {
		final int value = LocalDate.now().getDayOfWeek().getValue();
		final DiaSemanaEnum dia = DiaSemanaEnum.values()[value - 1];

		List<ItemVenda> itensCalculado = venda.getItens().stream()
				.filter(item -> item.getDisco() != null && item.getQuantidade() > 0)
				.map(item -> this.completarItemVenda(item, dia)).collect(Collectors.toList());

		venda.setItens(itensCalculado);
		venda.calcularValorTotal();
		venda.calcularTotalCashback();

		return this.vendaRepository.save(venda);
	}

	/**
	 * @param item
	 * @param dia
	 * @return ItemVenda
	 */
	public ItemVenda completarItemVenda(final ItemVenda item, final DiaSemanaEnum dia) {
		Disco disco = this.catalogoRepository.findById(item.getDisco().getId()).get();

		item.setValorUnitario(disco.getValor());

		CashbackID id = new CashbackID(dia, disco.getGenero());
		BigDecimal porcentagem = this.cashbackRepository.findById(id).get().getPorcentagem().divide(new BigDecimal(100));
		BigDecimal cashback = item.getValorUnitario().multiply(porcentagem).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal totalCashback = cashback.multiply(new BigDecimal(item.getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);

		item.setCachback(totalCashback);

		return item;

	}
}
