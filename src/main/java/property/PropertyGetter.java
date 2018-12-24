package property;

import com.typesafe.config.Config;

public abstract class PropertyGetter {
	
	public String get(String key) {
		return getConfig().getString(key);
	}
	
	protected abstract Config getConfig();
}
