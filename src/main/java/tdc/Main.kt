package tdc

import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Main

fun main(args: Array<String>) {
    Application.launch(FXMain::class.java, *args)
}