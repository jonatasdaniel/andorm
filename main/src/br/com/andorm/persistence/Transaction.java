package br.com.andorm.persistence;


public interface Transaction {

	void begin();
	void commit();
	void end();
	
}