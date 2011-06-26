package br.com.andorm.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Criteria {

	private final Class<?> clazz;
	private Restriction restriction;
	private final List<Condition> conditions;
	private final List<Order> orders;

	private Criteria(Class<?> clazz) {
		this.clazz = clazz;
		conditions = new ArrayList<Condition>();
		orders = new ArrayList<Order>();
	}
	
	public static Criteria from(Class<?> clazz) {
		return new Criteria(clazz);
	}

	public Restriction getRestriction() {
		return restriction;
	}

	public Criteria where(Restriction restriction) {
		return where(restriction, new Condition[] {});
	}

	public Criteria where(Restriction restriction, Condition... conditions) {
		this.restriction = restriction;
		this.conditions.addAll(Arrays.asList(conditions));
		
		return this;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public Criteria orderAscBy(String... fields) {
		for(String field : fields)
			orders.add(new Order(field, OrderType.Asc));
		
		return this;
	}
	
	public Criteria orderDescBy(String... fields) {
		for(String field : fields)
			orders.add(new Order(field, OrderType.Desc));
		
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public boolean hasRestriction() {
		return restriction != null;
	}

	public boolean hasConditions() {
		return conditions.size() > 0;
	}
	
	public boolean hasOrders() {
		return orders.size() > 0;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public List<Object> getParameters() {
		List<Object> params = new ArrayList<Object>();

		if (restriction != null)
			addParam(params, restriction);
		if(hasConditions())
			for(Condition c : getConditions())
				addParam(params, c.getRestriction());

		return params;
	}

	private void addParam(List<Object> list, Restriction restriction) {
		list.add(restriction.getValue());
		if (restriction.hasConditions()) {
			for (Condition c : restriction.getConditions()) {
				Restriction r = c.getRestriction();
				addParam(list, r);
			}
		}
	}
}