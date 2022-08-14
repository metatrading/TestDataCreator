package tdc.dao

import com.typesafe.config.ConfigFactory
import org.springframework.stereotype.Component
import tdc.controller.InformationController
import tdc.logger.log
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.sql.Driver
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors

@Component
class ConnectionData(
    private val informationController: InformationController
) {
    var driver: Driver? = null
    var url: String = ""
    var userName: String = ""
    var password: String = ""
    var schema: String = ""

    val settingFilePrefix = "testcreatorjdbc"

    init {
        val jdbcProperties = ConfigFactory.load("jdbc.properties")
        if (!jdbcProperties.isEmpty) {
            if (jdbcProperties.hasPath("jdbc.url")) {
                url = jdbcProperties.getString("jdbc.url")
            }
            if (jdbcProperties.hasPath("jdbc.username")) userName = jdbcProperties.getString("jdbc.username")
            if (jdbcProperties.hasPath("jdbc.password")) password = jdbcProperties.getString("jdbc.password")
            if (jdbcProperties.hasPath("jdbc.schema")) schema = jdbcProperties.getString("jdbc.schema")
        }
        loadVariableSettings()
    }

    /**
     * 設定値ロード.
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @Throws(FileNotFoundException::class, IOException::class, SQLException::class)
    fun loadVariableSettings() {
        val dir = System.getProperty("java.io.tmpdir")
        val checkPath = Paths.get(File(dir).toURI())

        // 未作成
        if (!Files.exists(checkPath)) {
            log.warn("not exists variable settings.")
            return
        }
        val paths = Files.find(
            Paths.get(File(dir).toURI()), 1,
            { p: Path, _: BasicFileAttributes? -> p.toFile().name.startsWith(settingFilePrefix) })
            .collect(Collectors.toList())

        // 未作成
        if (paths.isEmpty()) return

        // exists
        val file = paths[0].toFile()
        FileReader(file).use { `in` ->
            val prop = Properties()
            log.debug("prop:{}", prop)
            prop.load(`in`)
            url = prop.getProperty("url")
            userName = prop.getProperty("username")
            password = prop.getProperty("password")
            schema = prop.getProperty("schema")
            driver = DriverManager.getDriver(url)
        }
    }

    fun save(url: String, userName: String, password: String, schema: String) {
        // 設定値を保存。
        var file: File? = null
        try {
            // 既存ファイルの削除
            val dir = System.getProperty("java.io.tmpdir")
            val checkPath = Paths.get(File(dir).toURI())
            if (!Files.exists(checkPath)) return
            try {
                Files.find(checkPath, Int.MAX_VALUE,
                    { p: Path, attr: BasicFileAttributes ->
                        !attr.isDirectory &&
                                p.toFile().name.startsWith(settingFilePrefix)
                    }).use { v ->
                    v.forEach { t: Path? ->
                        try {
                            t?.let { Files.delete(it) }
                        } catch (e: IOException) {
                            thenError(e)
                        }
                    }
                }
            } catch (e: Exception) {
                thenError(e)
            }
            file = File.createTempFile(settingFilePrefix, null)
        } catch (e1: IOException) {
            thenError(e1)
        }

        // 保存
        try {
            Objects.requireNonNull(file)?.let {
                FileWriter(it).use { out ->
                    val prop = Properties()
                    prop["url"] = url
                    prop["username"] = userName
                    prop["password"] = password
                    prop["schema"] = schema
                    prop.store(out, "")

                    informationController.setInformation("設定を保存しました。")
                }
            }
        } catch (e: Exception) {
            thenError(e)
        }

        this.url = url
        this.schema = schema
        this.userName = userName
        this.password = password
    }

    private fun thenError(e: java.lang.Exception) {
        log.error(e.message, e)
        informationController.setInformation(e.message)
    }
}
