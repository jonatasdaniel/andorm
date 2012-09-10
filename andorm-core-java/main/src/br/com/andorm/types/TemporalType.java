package br.com.andorm.types;

public enum TemporalType {

	Date("dd/MM/yyyy"),
	Time("hh:mm:ss"),
	DateTime(Date.mask() + " " + Time.mask());
	
	private String mask;
	
	private TemporalType(String mask) {
		this.mask = mask;
	}
	
	public String mask() {
		return mask;
	}
	
}