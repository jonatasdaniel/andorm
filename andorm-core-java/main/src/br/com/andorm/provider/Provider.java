package br.com.andorm.provider;

/**
 * 
 * @author jonatas-daniel
 *
 */
public interface Provider {

	<T> T newInstance(Class<T> of);
	
}