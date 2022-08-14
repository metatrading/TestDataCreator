package tdc.property.impl;

import java.text.Format;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import com.typesafe.config.ConfigException.BadPath;
import com.typesafe.config.ConfigException.Missing;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import tdc.property.PropertyGetter;

public final class PropertyDomainToValues extends PropertyGetter {
    protected static Config config = ConfigFactory.load("domainValue");
    protected static Format format = new MessageFormat("{0}");

    public static List<String> getValues(String key) {
	if ("".equals(key))
	    return Collections.emptyList();
	try {
	    return config.getStringList(key);
	} catch (Missing | BadPath e) {
	    // ドメインに応じた値が無くても続行。ただし、エラーは出す。
	    System.err.println("[" + key + "]ドメイン取得パスもしくはドメインが存在しないため、ドメインは利用しません。");
	    return Collections.emptyList();
	}
    }

    @Override
    protected Config getConfig() {
	return config;
    }
}
