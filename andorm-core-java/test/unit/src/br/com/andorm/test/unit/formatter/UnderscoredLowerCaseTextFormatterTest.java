package br.com.andorm.test.unit.formatter;

import junit.framework.Assert;

import org.junit.Test;

import br.com.andorm.formatter.TextFormatter;
import br.com.andorm.formatter.UnderscoredLowerCaseTextFormatter;

public class UnderscoredLowerCaseTextFormatterTest {

	private TextFormatter formatter = new UnderscoredLowerCaseTextFormatter();
	
	@Test
	public void shouldFormatSingleWord() {
		String word = "Simple";
		String expected = "simple";
		String returned = formatter.format(word);
		Assert.assertNotNull(word);
		Assert.assertEquals(expected, returned);
	}
	
	@Test
	public void shouldFormatNullWord() {
		String word = null;
		String returned = formatter.format(word);
		Assert.assertNull(returned);
	}
	
	@Test
	public void shouldFormatCompositeWord() {
		String word = "SimpleWord";
		String expected = "simple_word";
		String returned = formatter.format(word);
		Assert.assertNotNull(word);
		Assert.assertEquals(expected, returned);
	}
	
}