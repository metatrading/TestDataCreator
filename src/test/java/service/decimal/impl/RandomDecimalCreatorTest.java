package service.decimal.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import tdc.db.ColumnDefinitionDto;
import org.junit.jupiter.api.Test;
import tdc.service.decimal.impl.RandomDecimalCreator;

public class RandomDecimalCreatorTest  {

	@Test
	public void test01() {
		RandomDecimalCreator rdc = new RandomDecimalCreator();
		String tableName="any";
		String columnName="any";
		Class<?> dataType = BigDecimal.class;
		int size = 10;
		int degits= 4;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int dataNo = 123;
		BigDecimal ret = rdc.create(def, 1, dataNo);
		
		System.out.println(ret);
		assertNotNull(ret);
		assertThat(ret.scale()).isEqualTo(4);
	}
}
