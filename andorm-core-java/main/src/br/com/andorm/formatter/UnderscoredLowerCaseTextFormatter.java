package br.com.andorm.formatter;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class UnderscoredLowerCaseTextFormatter implements TextFormatter {

	@Override
	public String format(String text) {
		if(text != null) {
			StringBuilder builder = new StringBuilder();
			
			for (char c : text.toCharArray()) {
				if(Character.isUpperCase(c) && builder.length() > 0) {
					builder.append("_");
				}
				builder.append(String.valueOf(c).toLowerCase());
			}
			
			return builder.toString();
		} else {
			return null;
		}
	}

}
