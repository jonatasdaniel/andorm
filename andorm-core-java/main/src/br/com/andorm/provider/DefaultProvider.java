package br.com.andorm.provider;

import br.com.andorm.reflection.Reflection;

public class DefaultProvider implements Provider {

	@Override
	public <T> T newInstance(Class<T> of) {
		return Reflection.newInstance(of);
	}

}