package com.github.eltonsandre.discosvinil.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.discosvinil.api.repository.entity.Disco;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 19:24:32
 */
@Service
public class CatalogoService {

	/**
	 * <code><pre></pre></code>
	 *
	 * @param filtro
	 * @param pageable
	 * @return Page<Album>
	 */
	public Page<Disco> filtrar(final String filtro, final Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
