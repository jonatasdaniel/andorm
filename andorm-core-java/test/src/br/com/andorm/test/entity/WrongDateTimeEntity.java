package br.com.andorm.test.entity;

import java.util.Calendar;

import br.com.andorm.DateTime;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity
public class WrongDateTimeEntity {

	@PrimaryKey
	private Integer id;
	@DateTime
	private Calendar date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
