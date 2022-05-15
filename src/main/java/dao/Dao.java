package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

public class Dao {
	@Getter
	protected final String settingFilePrefix = "testcreatorjdbc";
	protected Logger logger = LoggerFactory.getLogger(Dao.class);
	protected Driver driver;
	@Getter
	protected SimpleStringProperty url = new SimpleStringProperty("");
	@Getter
	protected SimpleStringProperty user = new SimpleStringProperty("");
	@Getter
	protected SimpleStringProperty password = new SimpleStringProperty("");
	@Getter
	protected SimpleStringProperty schema = new SimpleStringProperty("");

	public Dao() throws FileNotFoundException, IOException, SQLException {
		Config jdbc = ConfigFactory.load("jdbc.properties");
		if (!jdbc.isEmpty()) {
			if (jdbc.hasPath("jdbc.url")) {
				url = new SimpleStringProperty(jdbc.getString("jdbc.url"));
			}
			if (jdbc.hasPath("jdbc.username"))
				user = new SimpleStringProperty(jdbc.getString("jdbc.username"));
			if (jdbc.hasPath("jdbc.password"))
				password = new SimpleStringProperty(jdbc.getString("jdbc.password"));
			if (jdbc.hasPath("jdbc.schema"))
				schema = new SimpleStringProperty(jdbc.getString("jdbc.schema"));
		}
		loadVariableSettings();
	}

	/**
	 * 接続.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Connection getConnection() throws Exception {

		Properties connectionProperty = new Properties();

		if (user.get().isEmpty()) {
			NullPointerException e = new NullPointerException("username がnullです。");
			throw e;
		}
		connectionProperty.setProperty("user", user.get());

		if (!password.get().isEmpty()) {
			connectionProperty.setProperty("password", password.get());
		}
		connectionProperty.setProperty("schema", schema.get());
		
		Connection con = null;
		logger.info("url : " + url);
		logger.info("prop[{}]", connectionProperty.entrySet());

		driver = DriverManager.getDriver(url.get());

		if (driver.acceptsURL(url.get())) {
			con = driver.connect(url.get(), connectionProperty);
		} else {
			Exception e = new Exception("接続情報が不正です。");
			throw e;
		}

		loadVariableSettings();

		return con;
	}

	/**
	 * 設定値ロード.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	private void loadVariableSettings() throws FileNotFoundException, IOException, SQLException {
		String dir = System.getProperty("java.io.tmpdir");
		Path checkPath = Paths.get(new File(dir).toURI());

		// 未作成
		if (!Files.exists(checkPath))
			return;
		List<Path> paths = Files.find(Paths.get(new File(dir).toURI()), 1, (p, attr) -> {
			return p.toFile().getName().startsWith(settingFilePrefix);
		}).collect(Collectors.toList());

		// 未作成
		if (paths.isEmpty())
			return;

		File file = paths.get(0).toFile();
		try (FileReader in = new FileReader(file)) {
			Properties prop = new Properties();
			prop.load(in);
			url.set(prop.getProperty("url"));
			user.set(prop.getProperty("username"));
			password.set(prop.getProperty("password"));
			schema.set(prop.getProperty("schema"));

			driver = DriverManager.getDriver(url.get());
		}
	}
}
