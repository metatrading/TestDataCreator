package tdc.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import tdc.dao.Dao
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

@Component
@RequiredArgsConstructor
class JdbcConfigTabController(
    private val dao: Dao
) {
    lateinit var saveButton: Button
    lateinit var status: Label
    lateinit var schema: TextField
    lateinit var password: TextField
    lateinit var userName: TextField
    lateinit var url: TextField

    fun initialize() {
        url.text = dao.url.get()
        userName.text = dao.user.get()
        password.text = dao.password.get()
        schema.text = dao.schema.get()
    }

    @FXML
    fun saveButtonClicked(actionEvent: ActionEvent) {

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
                                p.toFile().name.startsWith(dao.settingFilePrefix)
                    }).use { v ->
                    v.forEach { t: Path? ->
                        try {
                            Files.delete(t)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            status.text = e.message
                        }
                    }
                }
            } catch (e: Exception) {
                status.text = e.message
            }
            file = File.createTempFile(dao.settingFilePrefix, null)
        } catch (e1: IOException) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e1.printStackTrace(pw)
            pw.flush()
            status.text = sw.toString()
            e1.printStackTrace()
        }

        // 保存
        try {
            Objects.requireNonNull(file)?.let {
                FileWriter(it).use { out ->
                    val prop = Properties()
                    prop["url"] = dao.url.get()
                    prop["username"] = dao.user.get()
                    prop["password"] = dao.password.get()
                    prop["schema"] = dao.schema.get()
                    prop.store(out, "")

                    // FIXME よくない！
                    dao.isConnectable
                    status.text = "設定を保存しました。"
                }
            }
        } catch (e: Exception) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            pw.flush()
            status.text = sw.toString()
            e.printStackTrace()
        }
    }


}