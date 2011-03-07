package br.com.andorm.entity;


import java.io.Serializable;
import java.util.Date;

import br.com.andorm.AutoIncrement;
import br.com.andorm.Column;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity(tableName="cliente")
public class Client implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@PrimaryKey
	@Column(name = "codigo")
	@AutoIncrement
	private Integer				id;

	private String				nome;

	private String				endereco;

	private Boolean				ativo;

	public Client() {}

	public Client(String nome, String endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}