package br.com.andorm.test.entity;

import java.util.Date;

import br.com.andorm.AutoInc;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity
public class DateTimeEntity {

	@PrimaryKey
	@AutoInc
	private Integer id;
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
