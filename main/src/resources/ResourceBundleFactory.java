package resources;

import java.util.ResourceBundle;


public final class ResourceBundleFactory {
	
	private static ResourceBundle bundle;
	
	private ResourceBundleFactory() {}
	
	public static ResourceBundle get() {
		if(bundle == null)
			bundle = ResourceBundle.getBundle("resources/messages");
		return bundle;
	}
	
}