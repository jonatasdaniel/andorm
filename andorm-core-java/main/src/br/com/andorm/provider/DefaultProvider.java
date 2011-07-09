package br.com.andorm.provider;

import br.com.andorm.reflection.Reflactor;

public class DefaultProvider implements Provider {

	@Override
	public <T> T newInstanceOf(Class<T> clazz) {
		return Reflactor.newInstance(clazz);
	}

}
