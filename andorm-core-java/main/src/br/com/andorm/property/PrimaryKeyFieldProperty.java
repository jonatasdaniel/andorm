package br.com.andorm.property;

import java.lang.reflect.Field;

import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.PropertyWriter;

public class PrimaryKeyFieldProperty extends FieldProperty {

	private final boolean autoInc;

	public PrimaryKeyFieldProperty(String columnName, Field field,
			PropertyWriter writer, PropertyReader reader, boolean notNull,
			Class<?> databaseFieldType, boolean autoInc) {
		super(columnName, field, writer, reader, notNull, databaseFieldType);
		this.autoInc = autoInc;
	}

	public boolean isAutoInc() {
		return autoInc;
	}

}