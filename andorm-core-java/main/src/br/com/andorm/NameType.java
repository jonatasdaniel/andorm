package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.config.NameTypes;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NameType {

	NameTypes value();
	
}