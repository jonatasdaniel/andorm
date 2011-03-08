package br.com.andorm.utils;

/**
 * 
 * @author jonatasdaniel
 * @since 08/03/2011
 * @version 0.1
 *
 */
public final class NameResolver {

	public static String toUnderscoreLowerCase(String value) {
		if(value == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for(char c : value.toCharArray()) {
			if(Character.isUpperCase(c)) {
				if(sb.length() > 0) {
					sb.append("_");
				}
				sb.append(String.valueOf(c).toLowerCase());
			} else
				sb.append(c);
		}
		
		return sb.toString();
	}
	
}