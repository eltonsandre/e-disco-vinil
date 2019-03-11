package com.github.eltonsandre.discosvinil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.eltonsandre.discosvinil.api.model.entity.Venda;
import com.github.eltonsandre.discosvinil.api.repository.query.VendasRepositoryQuery;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 23:06:35
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendasRepositoryQuery {

}
