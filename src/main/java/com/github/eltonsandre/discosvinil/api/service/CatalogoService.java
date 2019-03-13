package com.github.eltonsandre.discosvinil.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.model.DiscoFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 19:24:32
 */
@Service
public class CatalogoService {

	@Autowired
	private CatalogoRepository catalogoRepository;

	/**
	 * Consultar o catálogo de discos de forma paginada, filtrando por gênero e ordenando de forma crescente pelo nome
	 * do disco;
	 *
	 * @param filtro
	 * @param pageable
	 * @return Page<Album>
	 */
	public Page<Disco> filtrar(final DiscoFiltro filtro, final Pageable pageable) {
		//		if (GeneroEnum.keyOf(org.apache.commons.lang3.StringUtils.lowerCase(filtro.getGenero())) == null) {
		//			return null;
		//		}
		return this.catalogoRepository.filtrar(filtro, pageable);
	}

	/**
	 * @param al void
	 */
	public void salvar(final Disco disco) {
		this.catalogoRepository.save(disco);
	}

}
