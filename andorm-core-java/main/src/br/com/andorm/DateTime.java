package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.types.TemporalType;

/**
 * 
 * @author jonatas-daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateTime {

	TemporalType type() default TemporalType.DateTime;
	
}