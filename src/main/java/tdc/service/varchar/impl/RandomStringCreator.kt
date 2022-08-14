package tdc.service.varchar.impl

import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import tdc.db.ColumnDefinitionDto
import tdc.service.RandomDataCreator
import java.text.MessageFormat

@Component
class RandomStringCreator : RandomDataCreator<String?> {
    override fun create(def: ColumnDefinitionDto?, seed: Int, dataNo: Int): String {
        return if (def!!.size == 1) {
            val `val` = safeValue(seed + dataNo, def.size)
            `val`.toString()
        } else {
            // TODO
            // とりあえず、列名1文字目と数字と０パディングして返す.本当は、大文字の部分だけ抽出して結合して、桁に応じて切り下げて返す。
            // 0多すぎるので列物理名の文字長か列サイズどちらか小さいほうを採用する。
            val colSize: Int
            colSize = if (def.columnName.length > def.size) {
                def.size
            } else {
                def.columnName.length
            }
            val format = (def.columnName.substring(0, 1) + "{0,number,"
                    + StringUtils.repeat("0", colSize - 1) + "}")
            var formated = MessageFormat.format(format, safeValue(seed + dataNo, colSize - 1))

            // 桁数オーバーの考慮
            while (formated.length > colSize) {
                formated = create(def, seed, dataNo % 10)
            }
            formated
        }
    }

    private fun safeValue(`val`: Int, size: Int): Int {
        val safeVal: Int
        safeVal = if (`val`.toString().length > size) {
            `val` % 10
        } else {
            `val`
        }
        return safeVal
    }
}