package br.com.andorm.formatter;

public interface Formatter<T> {

	T parse(String s);
	String format(T t);
	
}