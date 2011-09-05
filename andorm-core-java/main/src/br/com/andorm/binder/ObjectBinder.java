package br.com.andorm.binder;

import java.lang.reflect.Method;

import android.database.Cursor;
import br.com.andorm.persistence.CursorHelper;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.property.Property;

import static br.com.andorm.reflection.Reflactor.*;

public class ObjectBinder {

	private final CursorHelper helper;
	private final EntityCache cache;
	private final Cursor cursor;

	public ObjectBinder(CursorHelper helper, EntityCache cache, Cursor cursor) {
		this.helper = helper;
		this.cache = cache;
		this.cursor = cursor;
	}
	
	public void bind(Object object) {
		for(String column : cache.getColumns()) {
			int columnIndex = cursor.getColumnIndex(column);
			
			Property property = cache.getPropertyByColumn(column);
			
			if(cursor.isNull(columnIndex)) {
				property.set(object, null);
			} else {
				Class<?> type = property.getDatabaseFieldType();
				
				Method cursorMethod = helper.getMethod(type);
				Object param = invoke(cursor, cursorMethod).withParams(columnIndex);
				
				property.set(object, param);
			}
		}
	}

}