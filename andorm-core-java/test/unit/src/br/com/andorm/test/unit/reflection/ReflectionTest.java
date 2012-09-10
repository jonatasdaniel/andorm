package br.com.andorm.test.unit.reflection;


public abstract class ReflectionTest {

	public static class Klass {
		private Integer integerValue;
		private String stringValue;

		public Integer getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

	}

}