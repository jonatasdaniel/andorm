package br.com.andorm;

import static br.com.andorm.utils.reflection.ReflectionUtils.in;
import static br.com.andorm.utils.reflection.ReflectionUtils.invoke;
import static com.jonatasdaniel.criteria.Restriction.like;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.test.AndroidTestCase;
import br.com.andorm.entity.BasicClient;
import br.com.andorm.persistence.AndroidQueryBuilder;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.persistence.PrimaryKeyProperty;
import br.com.andorm.persistence.Property;

import com.jonatasdaniel.criteria.Criteria;

public class QueryBuilderTest extends AndroidTestCase {
	
	public void testShouldHaveOneClause() {
		Criteria criteria = new Criteria(BasicClient.class);
		criteria.where(like("nome", "joaozinho"));
		
		AndroidQueryBuilder queryBuilder = new AndroidQueryBuilder(criteria, entityCache());
		queryBuilder.build();
		
		assertEquals("nome =?", queryBuilder.whereClause());
		assertTrue(queryBuilder.whereArgs().length == 1);
		assertEquals("joaozinho", queryBuilder.whereArgs()[0]);
		assertNull(queryBuilder.groupBy());
		assertNull(queryBuilder.having());
		assertNull(queryBuilder.orderBy());
	}
	
	private EntityCache entityCache() {
		EntityCache cache = new EntityCache(BasicClient.class, "basic_client");
		Field field = in(BasicClient.class).returnField("id");
		Method setMethod = in(BasicClient.class).returnSetMethodOf(field);
		Method getMethod = in(BasicClient.class).returnGetMethodOf(field);
		Property prop = new PrimaryKeyProperty("id", field, getMethod, setMethod, true);
		Method setPkMethod = in(EntityCache.class).returnMethod("setPk", PrimaryKeyProperty.class);
		setPkMethod.setAccessible(true);
		invoke(cache, setPkMethod).withParams(prop);
		
		field = in(BasicClient.class).returnField("nome");
		setMethod = in(BasicClient.class).returnSetMethodOf(field);
		getMethod = in(BasicClient.class).returnGetMethodOf(field);
		prop = new Property("nome", field, getMethod, setMethod);
		Method addPropertyMethod = in(EntityCache.class).returnMethod("add", Property.class);
		addPropertyMethod.setAccessible(true);
		invoke(cache, addPropertyMethod).withParams(prop);
		
		field = in(BasicClient.class).returnField("endereco");
		setMethod = in(BasicClient.class).returnSetMethodOf(field);
		getMethod = in(BasicClient.class).returnGetMethodOf(field);
		prop = new Property("endereco", field, getMethod, setMethod);
		addPropertyMethod = in(EntityCache.class).returnMethod("add", Property.class);
		addPropertyMethod.setAccessible(true);
		invoke(cache, addPropertyMethod).withParams(prop);
		
		return cache;
	}
}