package property.impl

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import dto.db.ColumnDefinitionDto
import property.PropertyGetter
import java.text.Format
import java.text.MessageFormat

class PropertyTableColumnToDomain : PropertyGetter() {
    override fun getConfig(): Config {
        return Companion.config
    }

    companion object {
        protected var config = ConfigFactory.load("tableToDomain")
        protected var format: Format = MessageFormat("{0}.{1}")
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