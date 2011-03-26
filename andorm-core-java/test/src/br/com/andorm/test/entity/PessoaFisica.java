package br.com.andorm.test.entity;

import br.com.andorm.Entity;

@Entity
public class PessoaFisica extends Pessoa {

	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}