package br.com.andorm.query;

import static br.com.andorm.query.Condition.and;
import static br.com.andorm.query.Condition.or;
import static br.com.andorm.query.Restriction.greaterThan;
import static br.com.andorm.query.Restriction.lessThan;
import static br.com.andorm.query.Restriction.like;
import static br.com.andorm.query.Restriction.match;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.andorm.query.querybuilder.JPAQueryBuilder;
import br.com.andorm.query.util.Person;

public class BasicWhereTest {

	@Test
	public void selectAllTest() {
		Criteria criteria = Criteria.from(Person.class);
		
		final String expected = "SELECT person FROM Person person";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 0);
	}
	
	@Test
	public void shouldHaveOneCondition() {
		Criteria criteria = Criteria.from(Person.class).where(like("nome", "a basic name"));
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 1);
		assertTrue(isEqual(list("a basic name"), criteria.getParameters()));
	}
	
	@Test
	public void shouldHaveTwoConditions() {
		Criteria criteria = Criteria.from(Person.class).where(like("nome", "a name"), and(match("idade", 18)));
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 AND person.idade = ?2";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 2);
		assertTrue(isEqual(list("a name", 18), criteria.getParameters()));
	}
	
	@Test
	public void shouldHaveThreeConditions() {
		Criteria criteria = Criteria.from(Person.class).where(like("nome", "a name"), and(match("idade", 18)), and(match("sexo", 'M')));
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 AND person.idade = ?2 AND person.sexo = ?3";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 3);
		assertTrue(isEqual(list("a name", 18, 'M'), criteria.getParameters()));
	}
	
	@Test
	public void shouldHaveNestedConditions() {
		Criteria criteria = Criteria.from(Person.class).where(like("nome", "a name"), and(greaterThan("idade", 18), or(lessThan("idade", 60))));
		
		final String expected = "SELECT person FROM Person person WHERE person.nome LIKE ?1 AND (person.idade > ?2 OR person.idade < ?3)";
		final String builded = new JPAQueryBuilder(criteria).build();
		assertEquals(expected, builded);
		assertTrue(criteria.getParameters().size() == 3);
		assertTrue(isEqual(list("a name", 18, 60), criteria.getParameters()));
	}
	
	private boolean isEqual(List<? extends Object> expected, List<? extends Object> received) {
		if(expected == null)
			return received == null;
		else if(received == null)
			return expected == null;
		else if(expected.size() != received.size())
			return false;
		else {
			for(int i = 0; i < expected.size(); i++) {
				if(!expected.get(i).equals(received.get(i)))
					return false;
			}
			return true;
		}
	}
	
	private List<? extends Object> list(Object... objects) {
		return Arrays.asList(objects);
	}
}