package com.github.eltonsandre.discosvinil.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.eltonsandre.discosvinil.api.model.VendaFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Venda;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 18:32:45
 */
public interface VendasRepositoryQuery {

	public Page<Venda> filtrar(VendaFiltro filtro, Pageable pageable);

}
