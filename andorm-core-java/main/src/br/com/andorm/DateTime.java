package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.types.TemporalType;

/**
 * 
 * @author jonatasdaniel
 * @since 09/04/2011
 * @version 0.9
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateTime {

	TemporalType type() default TemporalType.Date;
	
}