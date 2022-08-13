package property.impl;

import java.text.Format;
import java.text.MessageFormat;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import dto.db.ColumnDefinitionDto;
import property.PropertyGetter;

public final class PropertyTableColumnToDomain extends PropertyGetter {
	protected static Config config = ConfigFactory.load("tableToDomain");
	protected static Format format = new MessageFormat("{0}.{1}");

	public static String getDomain(ColumnDefinitionDto dto) {
		return getDomain(dto.getTableName(), dto.getColumnName());
	}

	private static String getDomain(String key) {
		try {
			return config.getString(key);
		} catch (ConfigException.Missing e) {
			// ドメインに応じた値が無くても続行。ただし、エラーは出す。
			System.err.println("[" + key + "]ドメイン取得パスもしくはドメインが存在しないため、ドメインは利用しません。");
			return "";
		}
	}

	private static String getDomain(String tableName, String columnName) {
		Object[] args = { tableName, columnName };
		return getDomain(format.format(args));
	}

	@Override
	protected Config getConfig() {
		return config;
	}
}
