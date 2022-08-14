package service.datetime.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import tdc.infrastructure.AppConfig;
import tdc.db.ColumnDefinitionDto;
import tdc.service.datetime.impl.DateDatasCreator;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class DateDatasCreatorTest {
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

        assertThat(list.size()).isEqualTo(2);

        LocalDateTime ldt = LocalDateTime.of(2018, 5, 10, 0, 0, 0, 0);
        assertThat(list.get(0).getTime()).isEqualTo(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000);
        assertThat(list.get(0)).isEqualTo(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));

        ldt = ldt.with(ChronoField.DAY_OF_MONTH, 11);
        assertThat(list.get(1).getTime()).isEqualTo(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000);
        assertThat(list.get(1)).isEqualTo(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
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

        assertThat(list.size()).isEqualTo(2);

        LocalDateTime ldt = LocalDateTime.of(2018, 5, 11, 0, 0, 0, 0);
        assertThat(list.get(0).getTime()).isEqualTo(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000);
        assertThat(list.get(0)).isEqualTo(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));

        ldt = ldt.with(ChronoField.DAY_OF_MONTH, 12);
        assertThat(list.get(1).getTime()).isEqualTo(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000);
        assertThat(list.get(1)).isEqualTo(new Date(ldt.toEpochSecond(ZoneOffset.ofHours(9)) * 1000));
    }
}
