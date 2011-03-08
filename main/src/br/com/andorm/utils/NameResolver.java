package br.com.andorm.utils;


public final class NameResolver {

	public static String toUnderscoreLowerCase(String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(value.toLowerCase());
		
		return sb.toString();
	}
	
}