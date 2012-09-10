package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.provider.DefaultProvider;
import br.com.andorm.provider.Provider;
import br.com.andorm.types.TextFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	Class<? extends Provider> provider() default DefaultProvider.class;
	TextFormat textFormat() default TextFormat.Underscored;
	
}