package com.github.eltonsandre.discosvinil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.eltonsandre.discosvinil.api.model.entity.Cashback;
import com.github.eltonsandre.discosvinil.api.model.entity.CashbackID;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 23:17:57
 */
public interface CashbackRepository extends JpaRepository<Cashback, CashbackID> {

}
