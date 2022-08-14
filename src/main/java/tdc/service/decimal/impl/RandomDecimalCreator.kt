package tdc.service.decimal.impl

import org.apache.commons.lang3.RandomUtils
import org.springframework.stereotype.Component
import tdc.db.ColumnDefinitionDto
import tdc.service.RandomDataCreator
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class RandomDecimalCreator : RandomDataCreator<BigDecimal?> {
    override fun create(cellDef: ColumnDefinitionDto?, seed: Int, dataNo: Int): BigDecimal {
        // double random = RandomUtils.nextDouble(0, Math.pow(10, def.getSize()
        // - def.getDegits()));
        // BigDecimal val = BigDecimal.valueOf(random).setScale(def.getDegits(),
        // RoundingMode.HALF_DOWN);
        val mergeSeed = seed + dataNo

        // 初期値
        val stringSeisu = mergeSeed.toString()

        // 小数生成
        val intShosu = RandomUtils.nextInt(0, Math.pow(10.0, cellDef!!.digits.toDouble()).toInt())

        // 整数＋小数
        val constructorValue = "$stringSeisu.$intShosu"

        // DECIMAL生成
        var `val` = BigDecimal(constructorValue)
        `val` = `val`.setScale(cellDef.digits, RoundingMode.HALF_DOWN)
        if (`val`.precision() > cellDef.size) {
            `val` = `val`.remainder(BigDecimal.TEN.multiply(BigDecimal.valueOf(cellDef.size.toLong())))
        }
        return `val`
    }
}