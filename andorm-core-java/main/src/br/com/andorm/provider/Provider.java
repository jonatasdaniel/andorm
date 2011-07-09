package br.com.andorm.provider;

public interface Provider {

	<T> T newInstanceOf(Class<T> clazz);
	
}