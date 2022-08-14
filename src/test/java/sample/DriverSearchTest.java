package sample;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DriverSearchTest {

	Logger logger = LoggerFactory.getLogger(DriverSearchTest.class);

	@Test
	public void test01() throws IOException, ClassNotFoundException {


	}
	
	@Test
	public void test02() {
		
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			System.out.println(driver.getClass().getName());
		}
	}
}
