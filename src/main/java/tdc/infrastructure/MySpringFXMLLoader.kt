package tdc.infrastructure

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback
import lombok.RequiredArgsConstructor
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import tdc.logger.log
import java.io.IOException

@Component
@Scope(scopeName = "prototype")
@RequiredArgsConstructor
open class MySpringFXMLLoader(
    private val context: ApplicationContext
) {
    // ★オリジナルの FXMLLoader を生成
    private val loader: FXMLLoader = FXMLLoader()

    init {
        // ★ControllerFactory に ApplicationContext を利用する
        loader.controllerFactory = Callback { requiredType: Class<*>? ->
            context.getBean(requiredType)
        }
    }

    @Throws(IOException::class)
    fun load(path: String?): Parent {
        return loader.load(
            MySpringFXMLLoader::class.java
                .classLoader.getResourceAsStream(path)
        )
    }

    fun <T> load(path: String): T {
        log.info("load fxml {}", path)
        return loader.load(
            MySpringFXMLLoader::class.java
                .classLoader.getResourceAsStream(path)
        )
    }

    fun <T> getController(): T {
        return loader.getController()
    }

}