package service.decimal.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class RandomDecimalCreatorTest extends TestCase {

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
		assertThat(ret.scale(), is(4));
	}
}
