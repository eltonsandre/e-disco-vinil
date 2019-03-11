package com.github.eltonsandre.discosvinil.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.eltonsandre.discosvinil.api.model.DiscoFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;
import com.github.eltonsandre.discosvinil.api.util.MonetaryUtils;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 10 de mar de 2019 20:04:17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CatalogoRepositoryTest {

	@Autowired
	CatalogoRepository catalogoRepository;

	@Rule
	ExpectedException thrown = ExpectedException.none();

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository#save(com.github.eltonsandre.discosvinil.api.model.Disco)}.
	 */
	@Test
	public void testSalvarUmDisco() {
		Disco discoSalvar = criarDisco();
		Disco discoSalvo = this.catalogoRepository.save(discoSalvar);

		assertThat(discoSalvo.getId()).isNotNull();
		assertThat(discoSalvo.getNome()).isNotBlank();
		assertThat(discoSalvo.getGenero()).isEqualTo(GeneroEnum.ROCK);
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.repository.query.CatalogoRepositoryQuery#filtrar(com.github.eltonsandre.discosvinil.api.model.DiscoFiltro, org.springframework.data.domain.Pageable)}.
	 */
	@Test
	public void testFiltrar() {
		DiscoFiltro criarFiltro = criarFiltro();

	}

	static Disco criarDisco() {
		Disco discoFiltro = new Disco();
		discoFiltro.setGenero(GeneroEnum.ROCK);
		discoFiltro.setNome("Rock Star.");
		discoFiltro.setValor(MonetaryUtils.gerarValorRandom());

		return discoFiltro;
	}

	static DiscoFiltro criarFiltro() {
		DiscoFiltro discoFiltro = new DiscoFiltro();
		discoFiltro.setGenero("rock");
		discoFiltro.setNome("rock star");

		return discoFiltro;
	}

}
