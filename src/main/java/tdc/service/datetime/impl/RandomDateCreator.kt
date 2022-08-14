package tdc.service.datetime.impl

import org.springframework.stereotype.Component
import tdc.db.ColumnDefinitionDto
import tdc.service.RandomDataCreator
import java.sql.Date
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.time.temporal.ChronoField

@Component
class RandomDateCreator : RandomDataCreator<Date?> {
    // 2018/05/10 00:00:00.000
    private val ldt = LocalDateTime.of(2018, Month.JULY, 30, 0, 0, 0, 0)
    override fun create(cellDef: ColumnDefinitionDto?, seed: Int, dataNo: Int): Date {
        var retLdt: LocalDateTime? = null
        var dayOfYear = ldt[ChronoField.DAY_OF_YEAR]
        dayOfYear += seed + dataNo
        if (dayOfYear > 365) {
            val yearAdd = dayOfYear / 365
            dayOfYear = dayOfYear % 365 + 1
            retLdt = ldt.with(ChronoField.YEAR, (ldt[ChronoField.YEAR] + yearAdd).toLong())
            retLdt = retLdt.with(ChronoField.DAY_OF_YEAR, dayOfYear.toLong())
        } else {
            retLdt = ldt.with(ChronoField.DAY_OF_YEAR, dayOfYear.toLong())
        }
        return Date(retLdt.toInstant(ZoneOffset.ofHours(9)).epochSecond * 1000)
    }
}