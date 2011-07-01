package br.com.andorm.test.entity;

import java.util.Date;

import br.com.andorm.Entity;

@Entity
public class PessoaFisica extends Pessoa {

	private String cpf;
	private Date dataCadastro;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
