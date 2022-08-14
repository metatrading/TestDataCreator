package tdc.dao

import com.typesafe.config.ConfigFactory
import javafx.beans.property.SimpleStringProperty
import org.springframework.stereotype.Component
import tdc.logger.log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors
@Component
open class Dao {

    val settingFilePrefix = "testcreatorjdbc"
    private var driver: Driver? = null
    var url = SimpleStringProperty("")
    var user = SimpleStringProperty("")
    var password = SimpleStringProperty("")
    var schema = SimpleStringProperty("")

    init {
        val jdbcProperties = ConfigFactory.load("jdbc.properties")
        if (!jdbcProperties.isEmpty) {
            if (jdbcProperties.hasPath("jdbc.url")) {
                url = SimpleStringProperty(jdbcProperties.getString("jdbc.url"))
            }
            if (jdbcProperties.hasPath("jdbc.username")) user = SimpleStringProperty(jdbcProperties.getString("jdbc.username"))
            if (jdbcProperties.hasPath("jdbc.password")) password = SimpleStringProperty(jdbcProperties.getString("jdbc.password"))
            if (jdbcProperties.hasPath("jdbc.schema")) schema = SimpleStringProperty(jdbcProperties.getString("jdbc.schema"))
        }
        loadVariableSettings()
    }

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
    val connection: Connection?
        get() {
            loadVariableSettings()
            return connect()
        }

    private fun connect(): Connection {
        val connectionProperty = Properties()
        if (user.get().isEmpty()) {
            val e = NullPointerException("username がnullです。")
            throw e
        }
        connectionProperty.setProperty("user", user.get())
        if (password.get().isNotEmpty()) {
            connectionProperty.setProperty("password", password.get())
        }
        connectionProperty.setProperty("schema", schema.get())
        log.info("url : $url")
        log.info("prop[{}]", connectionProperty.entries)
        driver = DriverManager.getDriver(url.get())

        val con: Connection = if (driver!!.acceptsURL(url.get())) {
            driver!!.connect(url.get(), connectionProperty)
        } else {
            val e = Exception("接続情報が不正です。")
            throw e
        }
        return con
    }

    /**
     * 設定値ロード.
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @Throws(FileNotFoundException::class, IOException::class, SQLException::class)
    private fun loadVariableSettings() {
        val dir = System.getProperty("java.io.tmpdir")
        val checkPath = Paths.get(File(dir).toURI())

        // 未作成
        if (!Files.exists(checkPath)) {
            log.warn("not exists variable settings.")
            return
        }
        val paths = Files.find(Paths.get(File(dir).toURI()), 1,
            { p: Path, _: BasicFileAttributes? -> p.toFile().name.startsWith(settingFilePrefix) }).collect(Collectors.toList())

        // 未作成
        if (paths.isEmpty()) return
        val file = paths[0].toFile()
        FileReader(file).use { `in` ->
            val prop = Properties()
            log.debug("prop:{}", prop)
            prop.load(`in`)
            url.set(prop.getProperty("url"))
            user.set(prop.getProperty("username"))
            password.set(prop.getProperty("password"))
            schema.set(prop.getProperty("schema"))
            driver = DriverManager.getDriver(url.get())
        }
    }
}