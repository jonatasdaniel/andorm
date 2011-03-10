package br.com.andorm.utils;



public class ReflectionTestClass {

	private String	nome;
	private Integer	idade;
	private Integer	peso;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public Integer calculo() {
		return idade * peso;
	}
	
	public Integer calculo(Integer i) {
		return calculo() * i;
	}

}