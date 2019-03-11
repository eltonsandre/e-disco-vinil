package com.github.eltonsandre.discosvinil.api.model;

import java.util.List;

import lombok.Data;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 18:29:56
 */
@Data
public class DiscoFiltro {

	private String nome;

	private String genero;

	private List<Long> idDiscos;

}
