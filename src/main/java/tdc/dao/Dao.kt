package tdc.dao

import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.util.*

@Component
open class Dao(
    private val connectionData: ConnectionData
) {

    private var driver: Driver? = null

    @get:Throws(Exception::class)
    val isConnectable: Unit
        get() {
            connection
        }

    /**
     * 接続.
     *
     * @return
     * @throws Exception
     */
    @get:Throws(Exception::class)
    val connection: Connection
        get() {
            return connect()
        }

    private fun connect(): Connection {
        val connectionProperty = Properties()
        if (connectionData.userName.isEmpty()) {
            val e = NullPointerException("username がnullです。")
            throw e
        }
        connectionProperty.setProperty("user", connectionData.userName)
        if (connectionData.password.isNotEmpty()) {
            connectionProperty.setProperty("password", connectionData.password)
        }
        connectionProperty.setProperty("schema", connectionData.schema)
        driver = DriverManager.getDriver(connectionData.url)

        val con: Connection = if (driver!!.acceptsURL(connectionData.url)) {
            driver!!.connect(connectionData.url, connectionProperty)
        } else {
            val e = Exception("接続情報が不正です。")
            throw e
        }
        return con
    }

}
