package com.github.eltonsandre.discosvinil.api.repository.query;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.github.eltonsandre.discosvinil.api.model.VendaFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Venda;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 18:36:56
 */
public class VendasRepositoryQueryImpl implements VendasRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	private static final String DATA_VENDA = "dataVenda";

	/* (non-Javadoc)
	 *
	 * @see
	 * com.github.eltonsandre.Vendasvinil.api.repository.query.CatalogoRepositoryQuery#filtrar(com.github.
	 * eltonsandre.Vendasvinil.api.model.VendaFiltro, org.springframework.data.domain.Pageable) */
	@Override
	public Page<Venda> filtrar(final VendaFiltro filtro, final Pageable pageable) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Venda> criteria = builder.createQuery(Venda.class);
		Root<Venda> root = criteria.from(Venda.class);

		Predicate[] predicates = this.criarRestricoes(filtro, builder, root);
		criteria.where(predicates);

		criteria.orderBy(builder.desc(root.get(DATA_VENDA)));

		TypedQuery<Venda> query = this.manager.createQuery(criteria);
		this.adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, this.total(filtro));
	}

	/**
	 * @param filtro
	 * @param builder
	 * @param root
	 * @return Predicate[]
	 */
	private Predicate[] criarRestricoes(final VendaFiltro filtro, final CriteriaBuilder builder, final Root<Venda> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getDataInicial() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(DATA_VENDA), LocalDateTime.of(filtro.getDataInicial(), LocalTime.MIN)));
		}

		if (filtro.getDataFinal() != null) {
			predicates
					.add(builder.lessThanOrEqualTo(root.get(DATA_VENDA), LocalDateTime.of(filtro.getDataFinal(), LocalTime.MAX)));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	/**
	 * @param query
	 * @param pageable void
	 */
	private void adicionarRestricoesDePaginacao(final TypedQuery<?> query, final Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	/**
	 * Verifica o total de linhas por filtro
	 *
	 * @param filtro
	 * @return Long
	 */
	private Long total(final VendaFiltro filtro) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Venda> root = criteria.from(Venda.class);

		Predicate[] predicates = this.criarRestricoes(filtro, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return this.manager.createQuery(criteria).getSingleResult();
	}

}
