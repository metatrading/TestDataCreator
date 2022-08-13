package service.varchar.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import dto.db.ColumnDefinitionDto;
import org.junit.jupiter.api.Test;

public class RandomStringCreatorTest {

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
		assertThat(param).isEqualTo("t00000000000005");
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
		assertThat(param).isEqualTo("5");
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
		assertThat(param).isEqualTo("s1");
	}
}
