package br.com.andorm.resources;

import java.util.ResourceBundle;


public final class ResourceBundleFactory {
	
	private static ResourceBundle bundle;
	
	private ResourceBundleFactory() {}
	
	public static ResourceBundle get() {
		if(bundle == null)
			bundle = ResourceBundle.getBundle("br/com/andorm/resources/messages");
		return bundle;
	}
	
}