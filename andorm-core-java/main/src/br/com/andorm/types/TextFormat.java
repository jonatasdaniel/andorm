package br.com.andorm.types;

import br.com.andorm.formatter.DefaultTextFormatter;
import br.com.andorm.formatter.TextFormatter;
import br.com.andorm.formatter.UnderscoredLowerCaseTextFormatter;

/**
 * 
 * @author jonatas-daniel
 *
 */
public enum TextFormat {

	Normal(new DefaultTextFormatter()),
	Underscored(new UnderscoredLowerCaseTextFormatter());
	
	private TextFormatter formatter;
	
	private TextFormat(TextFormatter formatter) {
		this.formatter = formatter;
	}
	
	public String format(String text) {
		return formatter.format(text);
	}
}