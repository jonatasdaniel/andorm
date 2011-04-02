package br.com.andorm.query;

import java.util.ArrayList;
import java.util.List;

public final class Restriction {

	private final Field field;
	private final LogicComparator comparator;
	private final Object value;
	
	private final List<Condition> conditions;

	public Restriction(Field field, LogicComparator comparator, Object value) {
		this.field = field;
		this.comparator = comparator;
		this.value = value;
		
		conditions = new ArrayList<Condition>();
	}

	public boolean hasConditions() {
		return conditions.size() > 0;
	}
	
	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public Field getField() {
		return field;
	}

	public LogicComparator getComparator() {
		return comparator;
	}

	public Object getValue() {
		return value;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public static Restriction like(String field, String value) {
		return new Restriction(new Field(field), LogicComparator.Like, value);
	}
	
	public static Restriction match(String field, Object value) {
		return new Restriction(new Field(field), LogicComparator.Equals, value);
	}
	
	public static Restriction greaterThan(String field, Object value) {
		return new Restriction(new Field(field), LogicComparator.GreaterThan, value);
	}
	
	public static Restriction lessThan(String field, Object value) {
		return new Restriction(new Field(field), LogicComparator.LessThan, value);
	}
	
}