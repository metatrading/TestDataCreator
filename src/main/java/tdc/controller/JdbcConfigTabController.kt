package tdc.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import tdc.dao.ConnectionData
import tdc.dao.Dao

@Component
@RequiredArgsConstructor
class JdbcConfigTabController(
    private val connectionData: ConnectionData
) {
    lateinit var saveButton: Button
    lateinit var schema: TextField
    lateinit var password: TextField
    lateinit var userName: TextField
    lateinit var url: TextField

    fun initialize() {
        url.text = connectionData.url
        userName.text = connectionData.userName
        password.text = connectionData.password
        schema.text = connectionData.schema
    }

    @FXML
    fun saveButtonClicked(actionEvent: ActionEvent) {
        connectionData.save(
            url.text,
            userName.text,
            password.text,
            schema.text
        )
    }

}