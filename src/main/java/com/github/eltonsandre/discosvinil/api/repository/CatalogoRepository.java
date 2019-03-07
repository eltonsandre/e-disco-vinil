package com.github.eltonsandre.discosvinil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.eltonsandre.discosvinil.api.repository.entity.Disco;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 23:23:29
 */
@Repository
public interface CatalogoRepository extends JpaRepository<Disco, Long> {

}
