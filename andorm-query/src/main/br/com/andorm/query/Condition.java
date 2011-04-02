package br.com.andorm.query;

public final class Condition {

	private final LogicalOperator operator;
	private final Restriction restriction;

	public Condition(LogicalOperator operator, Restriction restriction) {
		this.operator = operator;
		this.restriction = restriction;
	}

	public LogicalOperator getOperator() {
		return operator;
	}

	public Restriction getRestriction() {
		return restriction;
	}
	
	public static Condition and(Restriction restriction) {
		return and(restriction, new Condition[] {});
	}
	
	public static Condition and(Restriction restriction, Condition... conditions) {
		for(Condition c : conditions)
			restriction.addCondition(c);
		
		return new Condition(LogicalOperator.And, restriction);
	}
	
	public static Condition or(Restriction restriction) {
		return or(restriction, new Condition[] {});
	}
	
	public static Condition or(Restriction restriction, Condition... conditions) {
		for(Condition c : conditions)
			restriction.addCondition(c);
		
		return new Condition(LogicalOperator.Or, restriction);
	}

}