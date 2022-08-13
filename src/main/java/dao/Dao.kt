package dao

import com.typesafe.config.ConfigFactory
import javafx.beans.property.SimpleStringProperty
import org.slf4j.LoggerFactory
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

open class Dao {

    val settingFilePrefix = "testcreatorjdbc"
    private var logger = LoggerFactory.getLogger(Dao::class.java)
    private var driver: Driver? = null
    var url = SimpleStringProperty("")
    var user = SimpleStringProperty("")
    var password = SimpleStringProperty("")
    var schema = SimpleStringProperty("")

    init {
        val jdbc = ConfigFactory.load("jdbc.properties")
        if (!jdbc.isEmpty) {
            if (jdbc.hasPath("jdbc.url")) {
                url = SimpleStringProperty(jdbc.getString("jdbc.url"))
            }
            if (jdbc.hasPath("jdbc.username")) user = SimpleStringProperty(jdbc.getString("jdbc.username"))
            if (jdbc.hasPath("jdbc.password")) password = SimpleStringProperty(jdbc.getString("jdbc.password"))
            if (jdbc.hasPath("jdbc.schema")) schema = SimpleStringProperty(jdbc.getString("jdbc.schema"))
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
    protected val connection: Connection?
        get() {
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
            logger.info("url : $url")
            logger.info("prop[{}]", connectionProperty.entries)
            driver = DriverManager.getDriver(url.get())

            val con: Connection? = if (driver!!.acceptsURL(url.get())) {
                driver!!.connect(url.get(), connectionProperty)
            } else {
                val e = Exception("接続情報が不正です。")
                throw e
            }

            loadVariableSettings()
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
        if (!Files.exists(checkPath)) return
        val paths = Files.find(Paths.get(File(dir).toURI()), 1, { p: Path, _: BasicFileAttributes? -> p.toFile().name.startsWith(settingFilePrefix) }).collect(Collectors.toList())

        // 未作成
        if (paths.isEmpty()) return
        val file = paths[0].toFile()
        FileReader(file).use { `in` ->
            val prop = Properties()
            prop.load(`in`)
            url.set(prop.getProperty("url"))
            user.set(prop.getProperty("username"))
            password.set(prop.getProperty("password"))
            schema.set(prop.getProperty("schema"))
            driver = DriverManager.getDriver(url.get())
        }
    }
}