package com.github.eltonsandre.discosvinil.api.repository.query;

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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.github.eltonsandre.discosvinil.api.model.DiscoFiltro;
import com.github.eltonsandre.discosvinil.api.model.entity.Disco;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 18:36:56
 */
public class CatalogoRepositoryQueryImpl implements CatalogoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	/* (non-Javadoc)
	 *
	 * @see com.github.eltonsandre.discosvinil.api.repository.query.CatalogoRepositoryQuery#filtrar(com.github.
	 * eltonsandre.discosvinil.api.model.DiscoFiltro, org.springframework.data.domain.Pageable) */
	@Override
	public Page<Disco> filtrar(final DiscoFiltro filtro, final Pageable pageable) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Disco> criteria = builder.createQuery(Disco.class);
		Root<Disco> root = criteria.from(Disco.class);

		Predicate[] predicates = this.criarRestricoes(filtro, builder, root);
		criteria.where(predicates);

		criteria.orderBy(builder.asc(root.get("nome")));

		TypedQuery<Disco> query = this.manager.createQuery(criteria);
		this.adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, this.total(filtro));
	}

	/**
	 * @param filtro
	 * @param builder
	 * @param root
	 * @return Predicate[]
	 */
	private Predicate[] criarRestricoes(final DiscoFiltro filtro, final CriteriaBuilder builder,
			final Root<Disco> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		GeneroEnum genero = GeneroEnum.keyOf(org.apache.commons.lang3.StringUtils.lowerCase(filtro.getGenero()));
		if (genero != null) {
			predicates.add(builder.equal(root.get("genero"), genero));
			//		if (filtro.getGenero() != null) {
			//			predicates.add(builder.equal(root.get("genero"), filtro.getGenero()));
		}
		if (!CollectionUtils.isEmpty(filtro.getIdDiscos())) {
			predicates.add(root.get("id").in(filtro.getIdDiscos()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

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
	private Long total(final DiscoFiltro filtro) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Disco> root = criteria.from(Disco.class);

		Predicate[] predicates = this.criarRestricoes(filtro, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return this.manager.createQuery(criteria).getSingleResult();
	}

}
