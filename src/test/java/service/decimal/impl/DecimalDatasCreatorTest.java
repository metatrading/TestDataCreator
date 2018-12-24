package service.decimal.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.AppConfig;
import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DecimalDatasCreatorTest extends TestCase {

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

		assertThat(list.size(), is(2));
		for (BigDecimal bigDecimal : list) {
			assertThat(bigDecimal.scale(), is(4));
		}
	}
}
