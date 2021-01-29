package service.varchar.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.AppConfig;
import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class VarcharDatasCreatorTest extends TestCase {
	@Inject
	private VarcharDatasCreator vdc;

	@Before
	public void before() {
		System.out.println("BEFORE");
	}

	@Test
	public void test01() {
		vdc.setSeed(1);
		String tableName = "test";
		String columnName = "through_bank_code";
		Class<?> dataType = String.class;
		int size = 15;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int rowSize = 2;
		List<String> list = vdc.create(def, rowSize);

		assertThat(list.size(), is(2));
		assertThat(list.get(0), is("bankcode1"));
		assertThat(list.get(1), is("bankcode2"));
	}

	@Test
	public void test02() {
		vdc.setSeed(0);
		String tableName = "not_exists_table";
		String columnName = "through_bank_code";
		Class<?> dataType = String.class;
		int size = 15;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int rowSize = 2;
		List<String> list = vdc.create(def, rowSize);

		assertThat(list.size(), is(2));
		assertThat(list.get(0), is("t00000000000001"));
		assertThat(list.get(1), is("t00000000000002"));
	}
}
