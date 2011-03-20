package br.com.andorm.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jonatasdaniel.criteria.Condition;
import com.jonatasdaniel.criteria.Criteria;
import com.jonatasdaniel.criteria.LogicComparator;
import com.jonatasdaniel.criteria.LogicalOperator;
import com.jonatasdaniel.criteria.Restriction;


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
			whereClause = new StringBuilder();
			whereArgs = new ArrayList<String>(1);
			
			buildRestriction(criteria.getRestriction());
			
			if(criteria.hasConditions()) {
				comparators();
				operators();
				
				for(Condition c : criteria.getConditions())
					buildCondition(c);
			}
		}
		
		return this;
	}
	
	private void buildRestriction(Restriction restriction) {
		String fieldName = restriction.getField().getName();
		String columnName = cache.getPropertyByField(fieldName).getColumnName();
		whereClause.append(columnName).append(" =?");
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