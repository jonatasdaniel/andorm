package br.com.andorm.persistence;

import java.util.List;

import br.com.andorm.cache.PersistenceManagerCache;
import br.com.andorm.query.Criteria;

public class SQLitePersistenceManager implements PersistenceManager {

	private final PersistenceManagerCache cache;

	public SQLitePersistenceManager(PersistenceManagerCache cache) {
		this.cache = cache;
	}

	@Override
	public void save(Object entity) {

	}

	@Override
	public void update(Object entity) {

	}

	@Override
	public void delete(Object entity) {

	}

	@Override
	public <T> List<T> find(Class<T> of, Criteria query) {
		return null;
	}

	@Override
	public <T> T first(Class<T> of, Criteria query) {
		return null;
	}

	@Override
	public <T> T last(Class<T> of, Criteria query) {
		return null;
	}

	@Override
	public <T> T read(Class<T> of, Object pk) {
		return null;
	}

}