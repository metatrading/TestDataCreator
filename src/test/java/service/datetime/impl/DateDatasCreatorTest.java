package service.datetime.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
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
@ContextConfiguration(classes=AppConfig.class)
public class DateDatasCreatorTest extends TestCase {
	@Inject
	DateDatasCreator ddc;

	@Test
	public void test01() throws Exception {
		ddc.setSeed(1);
		int rowSize = 2;
		String tableName = "any";
		String columnName = "any";
		Class<?> dataType = Date.class;
		int size = 0;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		List<Date> list = ddc.create(def, rowSize);

		assertThat(list.size(), is(2));

		LocalDateTime ldt = LocalDateTime.of(2018, 5, 10, 0, 0, 0, 0);
		assertThat(list.get(0).getTime(), is(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
		assertThat(list.get(0), is(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000)));

		ldt = ldt.with(ChronoField.DAY_OF_MONTH, 11);
		assertThat(list.get(1).getTime(), is(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
		assertThat(list.get(1), is(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000)));
	}

	@Test
	public void test02() throws Exception {
		ddc.setSeed(2);
		int rowSize = 2;
		String tableName = "any";
		String columnName = "any";
		Class<?> dataType = Date.class;
		int size = 0;
		int degits = 0;
		ColumnDefinitionDto def = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		List<Date> list = ddc.create(def, rowSize);

		assertThat(list.size(), is(2));

		LocalDateTime ldt = LocalDateTime.of(2018, 5, 11, 0, 0, 0, 0);
		assertThat(list.get(0).getTime(), is(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
		assertThat(list.get(0), is(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000)));

		ldt = ldt.with(ChronoField.DAY_OF_MONTH, 12);
		assertThat(list.get(1).getTime(), is(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
		assertThat(list.get(1), is(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000)));
	}
}
