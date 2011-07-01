package br.com.andorm.test.property;

import java.math.BigDecimal;
import java.util.Date;

public class PropertyObject {

	private Integer simpleAttr;
	private Date dateAttr;
	private EnumTest enumerator;
	private BigDecimal bigDecimal;

	public Integer getSimpleAttr() {
		return simpleAttr;
	}

	public void setSimpleAttr(Integer simpleAttr) {
		this.simpleAttr = simpleAttr;
	}

	public Date getDateAttr() {
		return dateAttr;
	}

	public void setDateAttr(Date dateAttr) {
		this.dateAttr = dateAttr;
	}

	public EnumTest getEnumerator() {
		return enumerator;
	}

	public void setEnumerator(EnumTest enumerator) {
		this.enumerator = enumerator;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

}