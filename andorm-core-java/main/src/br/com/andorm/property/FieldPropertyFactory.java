package br.com.andorm.property;

import java.lang.reflect.Field;

import br.com.andorm.Column;
import br.com.andorm.PrimaryKey;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.PropertyWriter;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.types.TextFormat;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class FieldPropertyFactory {

	private final static String PRIMARY_KEY_FIELD_NAME = "id";
	
	private String columnName;
	private PropertyWriter writer;
	private PropertyReader reader;
	private boolean notNull;
	private Class<?> databaseFieldType;
	
	public FieldProperty create(PropertyWriter writer, PropertyReader reader, TextFormat textFormat, Field field) {
		notNull = false;
		databaseFieldType = field.getType();
		this.writer = writer;
		this.reader = reader;
		
		if(field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			if(column.name().length() > 0) {
				columnName = column.name();
			} else {
				columnName = textFormat.format(field.getName());
			}
			
			if(field.getAnnotation(Column.class).writer() != PropertyWriter.class) {
				this.writer = Reflection.newInstance(field.getAnnotation(Column.class).writer());
			}
			if(field.getAnnotation(Column.class).reader() != PropertyReader.class) {
				this.reader = Reflection.newInstance(field.getAnnotation(Column.class).reader());
			}
		}
		
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