package tdc.property.impl

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import tdc.db.ColumnDefinitionDto
import tdc.property.PropertyGetter
import java.io.File
import java.text.Format
import java.text.MessageFormat

class PropertyTableColumnToDomain(private val field: Config) : PropertyGetter() {
    override val config: Config
        get() = this.field

    companion object {
        protected var config = ConfigFactory.load("tableToDomain")
        protected var format: Format = MessageFormat("{0}.{1}")
        init{
            config = ConfigFactory.parseFile(File("tableDomain.txt"))
        }
        fun getDomain(dto: ColumnDefinitionDto): String {
            return getDomain(dto.tableName, dto.columnName)
        }

        private fun getDomain(key: String): String {
            return try {
                config.getString(key)
            } catch (e: ConfigException.Missing) {
                // ドメインに応じた値が無くても続行。ただし、エラーは出す。
                System.err.println("[$key]ドメイン取得パスもしくはドメインが存在しないため、ドメインは利用しません。")
                ""
            }
        }

        private fun getDomain(tableName: String, columnName: String): String {
            val args = arrayOf<Any>(tableName, columnName)
            return getDomain(format.format(args))
        }
    }

}