package com.github.eltonsandre.discosvinil.api.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 6 de mar de 2019 23:09:51
 */
@Getter
@Setter
public class VendaFiltro {
	private LocalDate dataInicial;
	private LocalDate dataFinal;
}
