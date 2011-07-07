package br.com.andorm.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.andorm.query.Condition;
import br.com.andorm.query.Criteria;
import br.com.andorm.query.Group;
import br.com.andorm.query.LogicComparator;
import br.com.andorm.query.LogicalOperator;
import br.com.andorm.query.Order;
import br.com.andorm.query.Restriction;


public class AndroidQueryBuilder {

	private final Criteria criteria;
	private final EntityCache cache;
	
	private List<String> whereArgs;
	private StringBuilder whereClause;
	private StringBuilder orderBy;
	private StringBuilder having;
	private StringBuilder groupBy;
	
	private Map<LogicalOperator, String> operators;
	private Map<LogicComparator, String> comparators;

	public AndroidQueryBuilder(Criteria criteria, EntityCache cache) {
		this.criteria = criteria;
		this.cache = cache;
	}
	
	public AndroidQueryBuilder build() {
		if(criteria.hasRestriction()) {
			comparators();
			
			whereClause = new StringBuilder();
			whereArgs = new ArrayList<String>(1);
			
			buildRestriction(criteria.getRestriction());
			
			if(criteria.hasConditions()) {
				operators();
				
				for(Condition c : criteria.getConditions())
					buildCondition(c);
			}
		}
		
		if(criteria.hasOrders()) {
			orderBy = new StringBuilder();
			int index = 0;
			for (Order order : criteria.getOrders()) {
				if(index > 0) {
					orderBy.append(", ");
				}
				orderBy.append(buildOrder(order));
				index++;
			}
		}
		
		if(criteria.hasGroups()) {
			groupBy = new StringBuilder();
			int index = 0;
			for (Group group : criteria.getGroups()) {
				if(index > 0) {
					groupBy.append(", ");
				}
				groupBy.append(buildGroup(group));
				index++;
			}
		}
		
		return this;
	}
	
	private String buildOrder(Order order) {
		StringBuilder builder = new StringBuilder();
		builder.append(order.getBy()).append(" ").append(order.getType().name().toUpperCase());
		return builder.toString();
	}
	
	private String buildGroup(Group group) {
		StringBuilder builder = new StringBuilder();
		builder.append(group.getBy());
		return builder.toString();
	}
	
	private void buildRestriction(Restriction restriction) {
		String fieldName = restriction.getField().getName();
		String columnName = cache.getPropertyByField(fieldName).getColumnName();
		whereClause.append(columnName);
		whereClause.append(comparators.get(restriction.getComparator()).toString().toUpperCase());
		whereClause.append("?");
		//whereClause.append(columnName).append(" =?");
		whereArgs.add(restriction.getValue().toString());
	}
	
	private void buildCondition(Condition condition) {
		Restriction restriction = condition.getRestriction();
		
		whereClause.append(" ");
		whereClause.append(operators.get(condition.getOperator()).toString().toUpperCase());
		whereClause.append(" ");
		
		if(restriction.hasConditions())
			whereClause.append("(");
		buildRestriction(restriction);
		if(restriction.hasConditions())
			whereClause.append(")");
	}
	
	private void comparators() {
		comparators = new HashMap<LogicComparator, String>();
		
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
		operators = new HashMap<LogicalOperator, String>();
		
		operators.put(LogicalOperator.And, " AND ");
		operators.put(LogicalOperator.Or, " OR ");
	}
	
	public String whereClause() {
		if(whereClause != null)
			return whereClause.toString();
		else
			return null;
	}
	
	public String[] whereArgs() {
		if(whereArgs != null) {
			String[] args = new String[whereArgs.size()];
			return whereArgs.toArray(args);
		}
		else
			return null;
	}
	
	public String orderBy() {
		if(orderBy != null)
			return orderBy.toString();
		else
			return null;
	}
	
	public String having() {
		if(having != null)
			return having.toString();
		else
			return null;
	}
	
	public String groupBy() {
		if(groupBy != null)
			return groupBy.toString();
		else
			return null;
	}
}