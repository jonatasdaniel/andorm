package br.com.viisi.teste.model;

import br.com.andorm.AutoInc;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity
public class Pessoa {

	@PrimaryKey
	@AutoInc
	private Integer id;
	private String nome;
	private String endereco;

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

}
