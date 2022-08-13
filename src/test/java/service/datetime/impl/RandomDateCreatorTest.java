package service.datetime.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import dto.db.ColumnDefinitionDto;
import org.junit.jupiter.api.Test;

public class RandomDateCreatorTest  {

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
		assertThat(date.getTime()).isEqualTo(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000);
		assertThat(date).isEqualTo(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
	}
}
