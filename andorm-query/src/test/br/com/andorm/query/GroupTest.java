package br.com.andorm.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.andorm.query.querybuilder.JPAQueryBuilder;
import br.com.andorm.query.util.Person;

public class GroupTest {

	@Test
	public void shouldHaveOneGroup() {
		Criteria query = Criteria.from(Person.class).groupBy("nome");
		
		final String expected = "SELECT person FROM Person person GROUP BY person.nome";
		final String builded = new JPAQueryBuilder(query).build();
		assertEquals(expected, builded);
	}
	
	@Test
	public void shouldHaveTwoGroups() {
		Criteria query = Criteria.from(Person.class).groupBy("nome", "idade");
		
		final String expected = "SELECT person FROM Person person GROUP BY person.nome, person.idade";
		final String builded = new JPAQueryBuilder(query).build();
		assertEquals(expected, builded);
	}
	
}