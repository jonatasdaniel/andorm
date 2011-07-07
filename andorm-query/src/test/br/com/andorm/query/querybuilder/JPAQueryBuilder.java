package br.com.andorm.query.querybuilder;

import java.util.HashMap;
import java.util.Map;

import br.com.andorm.query.Condition;
import br.com.andorm.query.Criteria;
import br.com.andorm.query.Group;
import br.com.andorm.query.LogicComparator;
import br.com.andorm.query.LogicalOperator;
import br.com.andorm.query.Order;
import br.com.andorm.query.Restriction;

public class JPAQueryBuilder {

	private final Criteria criteria;
	private final String className;
	private final String alias;
	
	private final Map<LogicalOperator, String> operators;
	private final Map<LogicComparator, String> comparators;
	
	private StringBuilder builder = new StringBuilder();
	private Integer paramCount = 1;

	public JPAQueryBuilder(Criteria criteria) {
		this.criteria = criteria;
		
		className = criteria.getClazz().getSimpleName();
		alias = className.toLowerCase();
		
		operators = new HashMap<LogicalOperator, String>();
		comparators = new HashMap<LogicComparator, String>();
		
		operators();
		comparators();
	}
	
	private void comparators() {
		comparators.put(LogicComparator.Like, " LIKE ");
		comparators.put(LogicComparator.Equals, " = ");
		comparators.put(LogicComparator.GreaterEqualsThan, " >= ");
		comparators.put(LogicComparator.GreaterThan, " > ");
		comparators.put(LogicComparator.IsNotNull, " IS NOT NULL ");
		comparators.put(LogicComparator.IsNull, " IS NULL ");
		comparators.put(LogicComparator.LessEqualsThan, " <= ");
		comparators.put(LogicComparator.LessThan, " < ");
	}
	
	private void operators() {
		operators.put(LogicalOperator.And, " AND ");
		operators.put(LogicalOperator.Or, " OR ");
	}
	
	public String build() {
		builder.append("SELECT ").append(alias).append(" FROM ").append(className).append(" ").append(alias);
		
		if(criteria.hasRestriction()) {
			builder.append(" WHERE ");
			buildRestriction(criteria.getRestriction());
		}
		
		if(criteria.hasConditions())
			for(Condition c : criteria.getConditions())
				buildCondition(c);
		
		if(criteria.hasOrders()) {
			builder.append(" ORDER BY ");
			int index = 1;
			for(Order order : criteria.getOrders()) {
				buildOrder(order);
				if(index++ < criteria.getOrders().size())
					builder.append(", ");
			}
		}
		
		if(criteria.hasGroups()) {
			builder.append(" GROUP BY ");
			int index = 1;
			for(Group group : criteria.getGroups()) {
				buildGroup(group);
				if(index++ < criteria.getGroups().size())
					builder.append(", ");
			}
		}
		
		return builder.toString().trim();
	}
	
	private void buildRestriction(Restriction r) {
		if(r.hasConditions())
			builder.append("(");
		builder.append(alias).append(".").append(r.getField().getName());
		builder.append(comparators.get(r.getComparator()));
		builder.append("?").append(paramCount++);
		
		if(r.hasConditions())
			for(Condition c : r.getConditions())
				buildCondition(c);
		
		if(r.hasConditions())
			builder.append(")");
	}
	
	private void buildCondition(Condition c) {
		builder.append(operators.get(c.getOperator()));
		buildRestriction(c.getRestriction());
	}
	
	private void buildOrder(Order o) {
		builder.append(alias).append(".");
		builder.append(o.getBy()).append(" ");
		builder.append(o.getType().toString().toUpperCase());
	}
	
	private void buildGroup(Group group) {
		builder.append(alias).append(".");
		builder.append(group.getBy());
	}
}