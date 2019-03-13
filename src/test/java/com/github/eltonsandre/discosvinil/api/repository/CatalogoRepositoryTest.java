package com.github.eltonsandre.discosvinil.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class CatalogoRepositoryTest {

	@Mock
	CatalogoRepository catalogoRepository;

	//	@Rule
	//	ExpectedException thrown = ExpectedException.none();

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.repository.CatalogoRepository#save(com.github.eltonsandre.discosvinil.api.model.Disco)}.
	 */
	@Test
	public void testSalvarUmDisco() {
		Disco discoSalvar = criarDisco();
		Disco discoSalvo = criarDisco();
		discoSalvo.setId(1L);

		when(this.catalogoRepository.save(discoSalvar)).thenReturn(discoSalvo);
		Disco discoSalvoReturn = this.catalogoRepository.save(discoSalvar);

		assertThat(discoSalvoReturn.getId()).isNotNull();
		assertThat(discoSalvoReturn.getNome()).isNotBlank();
		assertThat(GeneroEnum.ROCK).isEqualTo(discoSalvoReturn.getGenero());
	}

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.repository.query.CatalogoRepositoryQuery#filtrar(com.github.eltonsandre.discosvinil.api.model.DiscoFiltro, org.springframework.data.domain.Pageable)}.
	 */
	@Test
	public void testFiltrar() {
		DiscoFiltro criarFiltro = criarFiltro();

		when(this.catalogoRepository.filtrar(criarFiltro, PageRequest.of(0, 5))).thenReturn(null);

		assertNull(this.catalogoRepository.filtrar(criarFiltro, PageRequest.of(0, 5)));

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
		discoFiltro.setGenero("ROCK");
		discoFiltro.setNome("rock star");

		return discoFiltro;
	}

}
