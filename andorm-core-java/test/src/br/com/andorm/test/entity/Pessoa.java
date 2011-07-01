package br.com.andorm.test.entity;

import br.com.andorm.MappedSuperClass;
import br.com.andorm.PrimaryKey;

@MappedSuperClass
public class Pessoa {

	@PrimaryKey
	private Integer id;
	private String nome;
	private String endereco;
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
