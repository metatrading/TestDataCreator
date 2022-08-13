package sample;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DriverConnectTest {

	@Test
	public void test01() throws SQLException {
		String url = "jdbc:mysql://192.168.1.11/tickfeed";
		Driver driver = DriverManager.getDriver(url);
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "S@toshi01");
		Connection con = driver.connect("jdbc:mysql://192.168.1.11/tickfeed", prop);
		// Connection connection = DriverManager.getConnection(url, "root",
		// "S@toshi01");
		driver.acceptsURL("jdbc:mysql://192.168.1.11/tickfeed");

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from BITCOIN_ORDER");
		rs.next();
		System.out.println(rs.getInt(1));

		DatabaseMetaData metadata = con.getMetaData();
		ResultSet tables = metadata.getTables(null, "tickfeed", null, null);
		while (tables.next()) {
			// System.out.println(tables.getString("TABLE_NAME"));
		}

		ResultSet columns = metadata.getColumns(null, "tickfeed", "BITCOIN_ORDER", null);
		while (columns.next())
			System.out.println(columns.getString("COLUMN_NAME"));

		System.out.println("-------------------------------------");
		System.out.println("con.getCatalog():" + con.getCatalog());
		System.out.println("con.getSchema():" + con.getSchema());
		ResultSet pk = metadata.getPrimaryKeys(con.getCatalog(), con.getSchema(), "BITCOIN_ORDER");
		while (pk.next())
			System.out.println(pk.getString("COLUMN_NAME"));
	}
}
