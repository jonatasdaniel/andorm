package br.com.andorm.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResourceBundleFactory {

	private static ResourceBundleFactory instance;
	
	private ResourceBundle bundle;
	
	private ResourceBundleFactory() {
		bundle = ResourceBundle.getBundle("br.com.andorm.resources.messages");
	}
	
	public static ResourceBundleFactory get() {
		if(instance == null) {
			instance = new ResourceBundleFactory();
		}
		
		return instance;
	}
	
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	public String getString(String key, Object... args) {
		return MessageFormat.format(bundle.getString(key), args);
	}
	
}