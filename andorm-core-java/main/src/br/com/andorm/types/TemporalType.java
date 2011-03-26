package br.com.andorm.types;

/**
 * 
 * @author jonatasdaniel
 * @since 25/03/2011
 * @version 0.1
 *
 */
public enum TemporalType {

	Date("dd/MM/yyyy"),
	Time("hh:mm:ss"),
	DateTime(Date.getMask().concat(" ").concat(Time.getMask()));
	
	private String mask;
	
	TemporalType(String mask) {
		this.mask = mask;
	}
	
	public String getMask() {
		return mask;
	}
	
}