package br.com.andorm.test.unit.property;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class PropertyTest {

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