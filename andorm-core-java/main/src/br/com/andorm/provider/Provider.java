package br.com.andorm.provider;

public interface Provider {

	<T> T newInstance(Class<T> of);
	
}