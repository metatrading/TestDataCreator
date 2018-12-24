package sample;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

@RunWith(JUnit4.class)
public class DriverSearchTest {

	Logger logger = LoggerFactory.getLogger(DriverSearchTest.class);

	@Test
	public void test01() throws IOException, ClassNotFoundException {

		ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
		ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses();

		classes.stream().filter(e -> e.getName().contains("Driver")).forEach(e -> {
			try {
				Class<?> clazz = Class.forName(e.getName());

				if (clazz.isAssignableFrom(Driver.class)) {
					System.out.println(e.getName());
				}

			} catch (ClassNotFoundException | NoClassDefFoundError e1) {
				logger.warn("skip load class", e1.getMessage());
			}
		});
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
