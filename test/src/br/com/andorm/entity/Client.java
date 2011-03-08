package br.com.andorm.entity;


import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;

@Entity
public class Client {

	@PrimaryKey(autoInc = true)
	private Integer				id;

	private String				nome;

	private String				endereco;

	private Boolean				ativo;

	public Client() {}

	public Client(String nome, String endereco, boolean ativo) {
		this.nome = nome;
		this.endereco = endereco;
		this.ativo = ativo;
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
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if(ativo == null) {
			if(other.ativo != null)
				return false;
		} else if(!ativo.equals(other.ativo))
			return false;
		if(endereco == null) {
			if(other.endereco != null)
				return false;
		} else if(!endereco.equals(other.endereco))
			return false;
		if(nome == null) {
			if(other.nome != null)
				return false;
		} else if(!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}