package com.bguina.trader

import com.bguina.trader.di.DaggerControllerFactory
import com.bguina.trader.di.DaggerTraderComponent
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.net.URL
import javax.inject.Inject

class TraderApp : Application() {
    @Inject
    lateinit var daggerControllerFactory: DaggerControllerFactory

    init {
        DaggerTraderComponent
            .create()
            .inject(this)
    }

    private val appRoot: Parent = FXMLLoader(javaClass.getResource(RES_MAIN_FXML))
        .apply { controllerFactory = daggerControllerFactory }
        .load()

    private val appStyleRes: URL = requireNotNull(javaClass.getResource(RES_STYLE_CSS))

    override fun start(
        primaryStage: Stage
    ) = primaryStage.apply {
        icons
            .runCatching { add(Image(TraderApp::class.java.getResourceAsStream("ui/ic_app.png"))) }
            .exceptionOrNull()
            ?.printStackTrace()
        title = APP_NAME
        scene = Scene(appRoot)
            .apply { stylesheets.add(appStyleRes.toExternalForm()) }
    }.show()

    companion object {
        private const val APP_NAME = "Trader"
        private const val RES_STYLE_CSS = "ui/styles.css"
        private const val RES_MAIN_FXML = "ui/main.fxml"

        @JvmStatic
        fun main(
            args: Array<String>
        ) {
            launch(TraderApp::class.java, *args)
        }
    }
}
