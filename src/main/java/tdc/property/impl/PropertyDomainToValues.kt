package tdc.property.impl

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigException.BadPath
import com.typesafe.config.ConfigFactory
import tdc.property.PropertyGetter
import java.text.Format
import java.text.MessageFormat

class PropertyDomainToValues : PropertyGetter() {
    override val config: Config
        get() = Companion.config

    companion object {
        private var config = ConfigFactory.load("domainValue")
        private var format: Format = MessageFormat("{0}")
        @JvmStatic
		fun getValues(key: String): List<String> {
            return if ("" == key) emptyList() else try {
                config.getStringList(key)
            } catch (e: ConfigException.Missing) {
                // ドメインに応じた値が無くても続行。ただし、エラーは出す。
                System.err.println("[$key]ドメイン取得パスもしくはドメインが存在しないため、ドメインは利用しません。")
                emptyList()
            } catch (e: BadPath) {
                System.err.println("[$key]ドメイン取得パスもしくはドメインが存在しないため、ドメインは利用しません。")
                emptyList()
            }
        }
    }
}