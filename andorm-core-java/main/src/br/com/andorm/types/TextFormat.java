package br.com.andorm.types;

import java.lang.reflect.Method;

import br.com.andorm.reflection.Reflection;
import br.com.andorm.utils.NameResolver;

public enum TextFormat {

	Normal(Reflection.findMethod("toDefaultFormat", NameResolver.class).withArgs(String.class), NameResolver.class),
	Underscored(Reflection.findMethod("toUnderscoreLowerCase", NameResolver.class).withArgs(String.class), NameResolver.class);
	
	private Method formatter;
	private Object executeIn;
	
	private TextFormat(Method formatter, Object executeIn) {
		this.formatter = formatter;
		this.executeIn = executeIn;
	}
	
	public String format(String text) {
		Object result = Reflection.execute(formatter, executeIn).withArgs(text);
		if(result != null) {
			return (String) result;
		} else {
			return null;
		}
	}
}