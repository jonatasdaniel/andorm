package br.com.andorm.property;

import java.lang.reflect.Field;

import br.com.andorm.property.reader.DirectlyFieldReader;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.FieldAssignmentWriter;
import br.com.andorm.property.writer.PropertyWriter;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class FieldProperty {

	private final String columnName;
	private final Field field;
	private final PropertyWriter writer;
	private final PropertyReader reader;
	private final boolean notNull;
	private final Class<?> databaseFieldType;

	public FieldProperty(String columnName, Field field) {
		this(columnName, field, new FieldAssignmentWriter(),
				new DirectlyFieldReader(), false, field.getType());
	}

	public FieldProperty(String columnName, Field field, boolean notNull,
			Class<?> databaseFieldType) {
		this(columnName, field, new FieldAssignmentWriter(),
				new DirectlyFieldReader(), false, databaseFieldType);
	}

	public FieldProperty(String columnName, Field field, PropertyWriter writer,
			PropertyReader reader, boolean notNull, Class<?> databaseFieldType) {
		this.columnName = columnName;
		this.field = field;
		this.writer = writer;
		this.reader = reader;
		this.notNull = notNull;
		this.databaseFieldType = databaseFieldType;
	}

	public Object getValue(Object of) {
		return reader.read(of, field);
	}

	public void setValue(Object in, Object value) {
		writer.write(in, field, value);
	}

	public String getColumnName() {
		return columnName;
	}

	public Field getField() {
		return field;
	}

}