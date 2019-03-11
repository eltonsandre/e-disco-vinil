package com.github.eltonsandre.discosvinil.api.model.adapter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 21:04:27
 */
public class GeneroSerializer extends JsonSerializer<GeneroEnum> {

	/* (non-Javadoc)
	 *
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider) */
	@Override
	public void serialize(final GeneroEnum value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		// TODO Auto-generated method stub

		int i = 0;
		i++;
	}

}
