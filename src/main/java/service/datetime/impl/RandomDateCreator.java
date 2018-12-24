package service.datetime.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import org.springframework.stereotype.Component;

import dto.db.ColumnDefinitionDto;
import service.RandomDataCreator;

@Component
public class RandomDateCreator implements RandomDataCreator<Date> {

	// 2018/05/10 00:00:00.000
	private LocalDateTime ldt = LocalDateTime.of(2018, Month.JULY, 30, 0, 0, 0, 0);

	@Override
	public Date create(ColumnDefinitionDto cellDef, int seed, int dataNo) {
		LocalDateTime retLdt = null;
		int dayOfYear = ldt.get(ChronoField.DAY_OF_YEAR);

		dayOfYear = dayOfYear + seed + dataNo;
		if (dayOfYear > 365) {
			int yearAdd = dayOfYear / 365;
			dayOfYear = dayOfYear % 365 + 1;
			retLdt = ldt.with(ChronoField.YEAR, ldt.get(ChronoField.YEAR) + yearAdd);
			retLdt = retLdt.with(ChronoField.DAY_OF_YEAR, dayOfYear);
		} else {
			retLdt = ldt.with(ChronoField.DAY_OF_YEAR, dayOfYear);
		}
		return new Date(retLdt.toInstant(ZoneOffset.ofHours(9)).getEpochSecond() * 1000);
	}
}
