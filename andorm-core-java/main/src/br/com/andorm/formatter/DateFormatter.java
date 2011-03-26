package br.com.andorm.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.andorm.types.TemporalType;

public class DateFormatter implements Formatter<Date> {

	private final DateFormat format;
	
	public DateFormatter(TemporalType type) {
		format = new SimpleDateFormat(type.getMask());
	}

	@Override
	public Date parse(String s) {
		try {
			return format.parse(s);
		} catch(ParseException e) {
			return null;
		}
	}

	@Override
	public String format(Date t) {
		return format.format(t);
	}
	
}