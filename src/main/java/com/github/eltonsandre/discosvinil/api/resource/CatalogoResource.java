package com.github.eltonsandre.discosvinil.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository;
import com.github.eltonsandre.discosvinil.api.repository.entity.Disco;
import com.github.eltonsandre.discosvinil.api.service.CatalogoService;

/**
 * <b>O serviço deverá disponibilizar uma API REST contendo as seguintes operações:</b> <br />
 * - ○ Consultar o catálogo de discos de forma paginada, filtrando por gênero e ordenando de forma crescente
 * pelo nome do disco; <br />
 * - ○ Consultar o disco pelo seu identificador; <br />
 *
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 19:16:36
 */
@RestController
@RequestMapping("/catalogo")
public class CatalogoResource {

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private CatalogoRepository catalogoRepository;

	/**
	 * - ○ Consultar o catálogo de discos de forma paginada, filtrando por gênero e ordenando de forma
	 * crescente pelo nome do disco; <br />
	 *
	 * @param lancamentoFilter
	 * @param pageable
	 * @return Page<Lancamento>
	 */
	@GetMapping
	public Page<Disco> pesquisar(final String filtro, final Pageable pageable) {
		return this.catalogoService.filtrar(filtro, pageable);
	}

	/**
	 * - ○ Consultar o disco pelo seu identificador; <br />
	 * <code><pre></pre></code>
	 *
	 * @param codigo
	 * @return ResponseEntity<Lancamento>
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<Disco> buscarPeloCodigo(@PathVariable final Long codigo) {
		Optional<Disco> findById = this.catalogoRepository.findById(codigo);
		return findById.isPresent() ? ResponseEntity.ok(findById.get()) : ResponseEntity.notFound().build();
	}

}
