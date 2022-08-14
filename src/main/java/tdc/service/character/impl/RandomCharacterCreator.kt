package tdc.service.character.impl

import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import tdc.db.ColumnDefinitionDto
import tdc.service.RandomDataCreator
import java.text.MessageFormat

@Component
class RandomCharacterCreator : RandomDataCreator<String?> {
    override fun create(cellDef: ColumnDefinitionDto?, seed: Int, dataNo: Int): String {
        return if (cellDef!!.size == 1) {
            val `val` = safeValue(seed + dataNo, cellDef.size)
            `val`.toString()
        } else {
            val colSize = cellDef.size
            val format = (cellDef.columnName.substring(0, 1) + "{0,number," + StringUtils.repeat(
                "0",
                colSize - 1
            )
                    + "}")
            var formated = MessageFormat.format(format, safeValue(seed + dataNo, colSize - 1))

            // 桁数オーバーの考慮
            while (formated.length > colSize) {
                formated = create(cellDef, seed, dataNo % 10)
            }
            formated
        }
    }

    /**
     * オーバフローしない値生成.
     *
     * @param val
     * 基準値
     * @param size
     * データ型の桁数
     * @return データ型の桁数を超過しない値
     */
    private fun safeValue(`val`: Int, size: Int): Int {
        val safeVal: Int = if (`val`.toString().length > size) {
            `val` % 10
        } else {
            `val`
        }
        return safeVal
    }
}