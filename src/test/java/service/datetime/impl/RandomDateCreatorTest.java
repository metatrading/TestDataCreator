package service.datetime.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class RandomDateCreatorTest extends TestCase {

	@Test
	public void test01() throws ParseException {
		RandomDateCreator rdc = new RandomDateCreator();
		String tableName = "any";
		String columnName = "any";
		Class<?> dataType = Date.class;
		int size = 0;
		int degits = 0;
		ColumnDefinitionDto cellDef = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		int dataNo = 1;
		Date date = rdc.create(cellDef, 0, dataNo);
		LocalDateTime ldt = LocalDateTime.of(2018, 5, 11, 0, 0, 0, 0);
		assertThat(date.getTime(), is(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
		assertThat(date, is(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000)));
	}
}
