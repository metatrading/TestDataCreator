package tdc.property

import com.typesafe.config.Config

abstract class PropertyGetter {
    operator fun get(key: String?): String {
        return config.getString(key)
    }

    protected abstract val config: Config
}