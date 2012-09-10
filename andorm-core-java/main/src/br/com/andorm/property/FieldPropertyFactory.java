package br.com.andorm.property;

import java.lang.reflect.Field;

import br.com.andorm.PrimaryKey;
import br.com.andorm.property.reader.DirectlyFieldReader;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.FieldAssignmentWriter;
import br.com.andorm.property.writer.PropertyWriter;

public class FieldPropertyFactory {

	private final static String PRIMARY_KEY_FIELD_NAME = "id";
	
	private String columnName;
	private PropertyWriter writer;
	private PropertyReader reader;
	private boolean notNull;
	private Class<?> databaseFieldType;
	
	public FieldProperty create(Field field) {
		columnName = field.getName();
		writer = new FieldAssignmentWriter();
		reader = new DirectlyFieldReader();
		notNull = false;
		databaseFieldType = field.getType();
		
		FieldProperty property = null;
		if(field.isAnnotationPresent(PrimaryKey.class) || PRIMARY_KEY_FIELD_NAME.equals(field.getName())) {
			property = createPkProperty(field);
		} else {
			
		}
		
		return property;
	}
	
	private PrimaryKeyFieldProperty createPkProperty(Field field) {
		boolean autoInc = field.getAnnotation(PrimaryKey.class).autoInc();
		PrimaryKeyFieldProperty property = new PrimaryKeyFieldProperty(columnName, field, writer, reader, notNull, databaseFieldType, autoInc);
		return property;
	}
	
}