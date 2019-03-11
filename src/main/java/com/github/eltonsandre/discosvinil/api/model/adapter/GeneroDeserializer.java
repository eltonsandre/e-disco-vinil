package com.github.eltonsandre.discosvinil.api.model.adapter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.eltonsandre.discosvinil.api.model.entity.enumeration.GeneroEnum;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 9 de mar de 2019 19:36:41
 */
@Component
public class GeneroDeserializer extends JsonDeserializer<GeneroEnum> {
	/**
	 * Atributo serialVersionUID representa
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 *
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
	 * com.fasterxml.jackson.databind.DeserializationContext) */
	@Override
	public GeneroEnum deserialize(final JsonParser p, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		final String str = p.getText().trim();
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return GeneroEnum.keyOf(str);
	}

	// @SuppressWarnings("unchecked")
	// private Class<T> runtimeClass() {
	// ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
	// return (Class<T>) superclass.getActualTypeArguments()[0];
	// }

}
