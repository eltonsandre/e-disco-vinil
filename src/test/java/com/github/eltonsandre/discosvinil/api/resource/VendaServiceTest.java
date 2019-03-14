/**
 */
package com.github.eltonsandre.discosvinil.api.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.ItemVenda;
import com.github.eltonsandre.discosvinil.api.model.entity.Venda;
import com.github.eltonsandre.discosvinil.api.repository.CatalogoRepositoryTest;
import com.github.eltonsandre.discosvinil.api.service.VendaService;
import com.github.eltonsandre.discosvinil.api.util.MonetaryUtils;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">eltonsandre</a>
 * @date 14 de mar de 2019
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class VendaServiceTest {

	private static String[] valores = new String[] { "12.12", "23.23", "32.32", "45.43", "50.43" };
	private static int sizeList = 5;

	@Mock
	VendaService vendasService;

	/**
	 * Test method for
	 * {@link com.github.eltonsandre.discosvinil.api.resource.VendaResource#criar(com.github.eltonsandre.discosvinil.api.model.entity.Venda, org.springframework.data.domain.Pageable)}.
	 */
	@Test
	public void testCriar() {
		Venda criarVenda = criarVenda(sizeList, true, null);

		Venda vendaSalva = criarVenda(sizeList, true, 1L);
		vendaSalva.setDataVenda(LocalDateTime.now());

		when(this.vendasService.salvar(criarVenda)).thenReturn(vendaSalva);
		Venda save = this.vendasService.salvar(criarVenda);

		assertThat(save.getId()).isNotNull();
		assertThat(save.getDataVenda()).isNotNull();
		assertThat(save.getItens()).isNotEmpty();
		assertTrue(save.getItens().size() == sizeList);
	}

	/**
	 * @param size
	 * @param idItem
	 * @param idVenda
	 * @return
	 */
	public static Venda criarVenda(final long size, final boolean idItem, final Long idVenda) {
		Venda venda = new Venda();
		venda.setId(idVenda);
		venda.setItens(criarItensVenda(size, idItem, idVenda));

		venda.calcularTotalCashback();
		venda.calcularValorTotal();

		return venda;

	}

	/**
	 * @param size
	 * @param idItem
	 * @param idVenda
	 * @return
	 */
	public static List<ItemVenda> criarItensVenda(final long size, final boolean idItem, final Long idVenda) {
		List<ItemVenda> itens = new ArrayList<>();

		for (long idI = 1; idI <= size; idI++) {
			ItemVenda item = new ItemVenda();
			item.setId(idItem ? idI : null);
			item.setIdVenda(new Venda(idVenda));

			item.setValorUnitario(MonetaryUtils.money(valores[(int) idI - 1]));
			item.setCachback(new BigDecimal(idI));
			item.setQuantidade((int) idI);

			Disco disco = CatalogoRepositoryTest.criarDisco();
			disco.setId(idI);

			item.setDisco(disco);
			itens.add(item);
		}
		return itens;
	}

}
