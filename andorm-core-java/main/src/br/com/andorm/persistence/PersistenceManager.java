package br.com.andorm.persistence;

import java.util.List;

import br.com.andorm.query.Criteria;

public interface PersistenceManager {

	void save(Object entity);
	void update(Object entity);
	void delete(Object entity);
	<T> List<T> find(Class<T> of, Criteria query);
	<T> T first(Class<T> of, Criteria query);
	<T> T last(Class<T> of, Criteria query);
	<T> T read(Class<T> of, Object pk);
	
}