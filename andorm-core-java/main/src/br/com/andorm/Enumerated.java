package br.com.andorm;

import br.com.andorm.types.EnumType;

public @interface Enumerated {

	EnumType type() default EnumType.Ordinal;
	
}