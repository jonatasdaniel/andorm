package br.com.andorm.query;

import static br.com.andorm.query.Restriction.like;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.andorm.query.querybuilder.JPAQueryBuilder;
import br.com.andorm.query.util.Person;

public class OrderTest {

	@Test
	public void simpleOrderByAscTest() {
		Criteria criteria = new Criteria(Person.class);
		
		criteria.where(like("nome", "a name")).orderAscBy("idade");
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 ORDER BY person.idade ASC";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 1);
	}
	
	@Test
	public void simpleOrderByDescTest() {
		Criteria criteria = new Criteria(Person.class);
		
		criteria.where(like("nome", "a name")).orderDescBy("idade");
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 ORDER BY person.idade DESC";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 1);
	}
	
	@Test
	public void multipleOrderByAscTest() {
		Criteria criteria = new Criteria(Person.class);
		
		criteria.where(like("nome", "a name")).orderAscBy("idade", "data_nascimento");
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 ORDER BY person.idade ASC, person.data_nascimento ASC";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 1);
	}
	
	@Test
	public void multipleOrderByDescTest() {
		Criteria criteria = new Criteria(Person.class);
		
		criteria.where(like("nome", "a name")).orderDescBy("idade", "data_nascimento");
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 ORDER BY person.idade DESC, person.data_nascimento DESC";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 1);
	}
	
}