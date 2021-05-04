package com.bguina.trader.ui

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.apache.logging.log4j.Logger
import java.net.URL
import java.util.*
import javax.inject.Inject

class MainController @Inject constructor(
    private val logger: Logger
) : Initializable {

    private val javaVersion: String = System.getProperty(ENV_PROP_JAVA_VERSION)
    private val javaFxVersion: String = System.getProperty(ENV_PROP_JAVAFX_VERSION)

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

    companion object {
        private const val ENV_PROP_JAVA_VERSION = "java.version"
        private const val ENV_PROP_JAVAFX_VERSION = "javafx.version"
    }
}
