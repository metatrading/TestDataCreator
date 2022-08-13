package service.varchar.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import app.AppConfig;
import dto.db.ColumnDefinitionDto;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class VarcharDatasCreatorTest {
	@Inject
	private VarcharDatasCreator vdc;

	@BeforeEach
	public static void before() {
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

		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0)).isEqualTo("bankcode1");
		assertThat(list.get(1)).isEqualTo("bankcode2");
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

		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0)).isEqualTo("t00000000000001");
		assertThat(list.get(1)).isEqualTo("t00000000000002");
	}
}
