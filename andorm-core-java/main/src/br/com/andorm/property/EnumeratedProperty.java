package br.com.andorm.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static br.com.andorm.reflection.Reflactor.in;
import static br.com.andorm.reflection.Reflactor.invoke;
import br.com.andorm.types.EnumType;

public class EnumeratedProperty extends Property {

	private final EnumType type;

	public EnumeratedProperty(String columnName, Field field, Method getMethod,
			Method setMethod, EnumType type) {
		super(columnName, field, getMethod, setMethod, true,
				type == EnumType.Ordinal ? Integer.class : String.class);
		this.type = type;
	}

	public EnumeratedProperty(String columnName, Field field, Method getMethod,
			Method setMethod, boolean nullable, EnumType type) {
		super(columnName, field, getMethod, setMethod, nullable,
				type == EnumType.Ordinal ? Integer.class : String.class);
		this.type = type;
	}

	public EnumType getType() {
		return type;
	}

	@Override
	public Object get(Object of) {
		Enum<?> enumerator = (Enum<?>) super.get(of);
		if (enumerator != null) {
			if (type == EnumType.Ordinal)
				return enumerator.ordinal();
			else
				return enumerator.name();
		} else {
			return null;
		}
	}

	@Override
	public void set(Object in, Object value) {
		if (value != null) {
			if (type == EnumType.Ordinal) {
				Integer ordinal = (Integer) value;
				Method method = in(getField().getType()).returnMethod("values");
				Object[] values = (Object[]) invoke(
						getField().getType().getClass(), method)
						.withoutParams();
				super.set(in, values[ordinal]);
			} else if (type == EnumType.Name) {
				String name = (String) value;
				Method method = in(getField().getType()).returnMethod(
						"valueOf", String.class);
				Object enumValue = invoke(getField().getType().getClass(),
						method).withParams(name);
				super.set(in, enumValue);
			}
		} else {
			super.set(in, null);
		}
	}

}