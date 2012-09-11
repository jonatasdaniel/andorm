package br.com.andorm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.andorm.property.reader.DirectlyFieldReader;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.FieldAssignmentWriter;
import br.com.andorm.property.writer.PropertyWriter;
import br.com.andorm.provider.DefaultProvider;
import br.com.andorm.provider.Provider;
import br.com.andorm.types.TextFormat;

/**
 * 
 * @author jonatas-daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	Class<? extends Provider> provider() default DefaultProvider.class;
	TextFormat textFormat() default TextFormat.Underscored;
	Class<? extends PropertyWriter> propertyWriter() default FieldAssignmentWriter.class;
	Class<? extends PropertyReader> propertyReader() default DirectlyFieldReader.class;
	
}