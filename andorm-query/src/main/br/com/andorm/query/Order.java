package br.com.andorm.query;

public final class Order {

	private final String by;
	private final OrderType type;
	
	public Order(String by, OrderType type) {
		this.by = by;
		this.type = type;
	}

	public String getBy() {
		return by;
	}

	public OrderType getType() {
		return type;
	}
	
	public static Order orderAsc(String by) {
		return new Order(by, OrderType.Asc);
	}
	
	public static Order orderDesc(String by) {
		return new Order(by, OrderType.Desc);
	}
	
}