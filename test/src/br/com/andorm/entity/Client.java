package br.com.andorm.entity;


import java.io.Serializable;
import java.util.Date;

import br.com.andorm.AutoIncrement;
import br.com.andorm.Column;
import br.com.andorm.DateTime;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity("cliente")
public class Client implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@PrimaryKey
	@Column(name = "codigo")
	@AutoIncrement
	private Integer				id;

	private String				nome;

	private String				endereco;

	private Boolean				ativo;

	@DateTime
	@Column(name = "data_nascimento")
	private Date				dataNascimento;

	public Client() {}

	public Client(String nome, String endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public Client(String nome, String endereco, Date dataNascimento) {
		this(nome, endereco);
		this.dataNascimento = dataNascimento;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}