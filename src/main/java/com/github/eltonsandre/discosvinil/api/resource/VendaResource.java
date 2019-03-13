package com.github.eltonsandre.discosvinil.api.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.eltonsandre.discosvinil.api.model.VendaFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Venda;
import com.github.eltonsandre.discosvinil.api.repository.VendaRepository;
import com.github.eltonsandre.discosvinil.api.service.VendaService;

/**
 * - ○ Consultar todas as vendas efetuadas de forma paginada, filtrando pelo range de datas (inicial e final) da venda e
 * ordenando de forma decrescente pela data da venda; <br />
 * - ○ Consultar uma venda pelo seu identificador; <br />
 * - ○ Registrar uma nova venda de discos calculando o valor total de cashback considerando a tabela.
 *
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 19:30:47
 */
@CrossOrigin
@RestController
@RequestMapping("/vendas")
public class VendaResource {

	@Autowired
	private VendaService vendaService;

	@Autowired
	private VendaRepository vendaRepository;

	/**
	 * - ○ Consultar todas as vendas efetuadas de forma paginada, filtrando pelo range de datas (inicial e final) da
	 * venda e ordenando de forma decrescente pela data da venda; <br />
	 *
	 * @param filtro
	 * @param pageable
	 * @return Page<Venda>
	 */
	@GetMapping
	public Page<Venda> pesquisar(final VendaFiltro filtro, final Pageable pageable) {
		return this.vendaRepository.filtrar(filtro, pageable);
	}

	/**
	 * - ○ Consultar uma venda pelo seu identificador; <br />
	 *
	 * @param id
	 * @return ResponseEntity<Venda>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscarPeloId(@PathVariable final Long id) {
		Optional<Venda> lancamento = this.vendaRepository.findById(id);
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * - ○ Registra uma nova venda de discos calculando o valor total de cashback considerando a tabela.
	 *
	 * @param venda
	 * @return ResponseEntity<Venda>
	 */
	@PostMapping
	public ResponseEntity<Venda> criar(@Valid @RequestBody final Venda venda, final Pageable pageable) {
		Venda vendaSalvo = this.vendaService.salvar(venda, pageable);
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalvo);
	}

}
