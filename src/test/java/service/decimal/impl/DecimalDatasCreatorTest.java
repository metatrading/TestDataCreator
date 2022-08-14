package service.decimal.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import tdc.infrastructure.AppConfig;
import tdc.db.ColumnDefinitionDto;
import tdc.service.decimal.impl.DecimalDatasCreator;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class DecimalDatasCreatorTest{

	@Inject
	private DecimalDatasCreator ddc;

	@Test
	public void test01() {
		ddc.setSeed(1);
		String tableName = "any";
		String columnName = "any";
		Class<?> dataType = BigDecimal.class;
		int size = 10;
		int degits = 4;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int rowSize = 2;
		List<BigDecimal> list = ddc.create(def, rowSize);

		assertThat(list.size()).isEqualTo(2);
		for (BigDecimal bigDecimal : list) {
			assertThat(bigDecimal.scale()).isEqualTo(4);
		}
	}
}
