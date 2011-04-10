package br.com.andorm.test.entity;

import java.util.Date;

import br.com.andorm.DateTime;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;
import br.com.andorm.types.TemporalType;

@Entity
public class AnnotatedDateTimeEntity {

	@PrimaryKey
	private Integer id;
	@DateTime(type = TemporalType.DateTime)
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
