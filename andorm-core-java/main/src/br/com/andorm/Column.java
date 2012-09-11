package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.PropertyWriter;

/**
 * 
 * @author jonatas-daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	String name() default "";
	boolean notNull() default false;
	Class<? extends PropertyWriter> writer() default PropertyWriter.class;
	Class<? extends PropertyReader> reader() default PropertyReader.class;
	
}