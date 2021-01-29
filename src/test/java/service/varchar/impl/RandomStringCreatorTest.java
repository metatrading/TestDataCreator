package service.varchar.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;
import service.varchar.impl.RandomStringCreator;

@RunWith(JUnit4.class)
public class RandomStringCreatorTest extends TestCase {

	@Test
	public void test01() {
		RandomStringCreator rsc = new RandomStringCreator();

		String tableName = "test";
		String columnName = "through_bank_code";
		Class<?> dataType = String.class;
		int size = 15;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int dataNo = 4;
		String param = rsc.create(def, 1, dataNo);
		assertThat(param, is("t00000000000005"));
	}

	@Test
	public void test02() {
		RandomStringCreator rsc = new RandomStringCreator();

		String tableName = "test";
		String columnName = "through_bank_code";
		Class<?> dataType = String.class;
		int size = 1;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int dataNo = 4;
		String param = rsc.create(def, 1, dataNo);
		assertThat(param, is("5"));
	}

	@Test
	public void test03() {
		RandomStringCreator rsc = new RandomStringCreator();

		String tableName = "test";
		String columnName = "system_code";
		Class<?> dataType = String.class;
		int size = 2;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int dataNo = 10;
		String param = rsc.create(def, 1, dataNo);
		assertThat(param, is("s1"));
	}
}
