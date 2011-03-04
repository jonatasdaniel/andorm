package br.com.andorm.persistence;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 *
 */
public interface PersistenceManager {

	/**
	 * Open the database 
	 */
	void open();
	
	/**
	 * Close the database
	 */
	void close();
	
	/**
	 * Save a entity in the database
	 * @param o Entity to save
	 * @throws AndOrmPersistenceException
	 */
	void save(Object o) throws AndOrmPersistenceException;
	
	/**
	 * Delete a entity in the database
	 * @param o Entity to delete
	 * @throws AndOrmPersistenceException
	 */
	void delete(Object o) throws AndOrmPersistenceException;
	
	/**
	 * update a entity in the database
	 * @param o Entity to update
	 * @throws AndOrmPersistenceException
	 */
	void update(Object o) throws AndOrmPersistenceException;
	
	/**
	 * Return a entity contained in the database, specified by the pk
	 * @param entityClass Class of the entity to search
	 * @param pk PK of entity to search
	 * @return If entity was founded, return the entity, else return <code>null</code>
	 */
	Object get(Class<?> entityClass, Object pk);
	
}