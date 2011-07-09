package br.com.andorm.example.model.entity;

import br.com.andorm.MappedSuperClass;
import br.com.andorm.PrimaryKey;

@MappedSuperClass
public abstract class Pessoa {

	@PrimaryKey
	private Integer id;
	private String nome;
	private String email;
	private String contato;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

}