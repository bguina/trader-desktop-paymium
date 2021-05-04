package com.bguina.trader.ui.exchange_market

import com.bguina.trader.TraderApp
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.usecase.market.IGetExchangeMarketPriceUseCase
import com.bguina.trader.extensions.rx2.every
import com.bguina.trader.extensions.showAlertDialog
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.apache.logging.log4j.Logger
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExchangeMarketController @Inject constructor(
    private val logger: Logger,
    private val getExchangeMarketPriceUseCase: IGetExchangeMarketPriceUseCase
) : Initializable {
    @FXML
    lateinit var image: ImageView

    @FXML
    lateinit var title: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        image.image = Image(TraderApp::class.java.getResourceAsStream("ui/ic_paymium.png"))
        observe()
    }

    private fun observe() {
        getExchangeMarketPriceUseCase.getMarketPrice(
            exchange = EExchange.PAYMIUM,
            market = EMarket.BTC_EUR
        )
            .every(5, TimeUnit.SECONDS)
            .doOnNext { silentFailure = true }
            .subscribe({ marketPrice ->
                title.text = "${EExchange.PAYMIUM.name} ${EMarket.BTC_EUR.shortName}: " + marketPrice.format()
            }) {
                logger.warn("${it.javaClass.simpleName}: ${it.message.orEmpty()}", it)
                if (!silentFailure) it.showAlertDialog()
                silentFailure = false
                observe()
            }
    }

    private var silentFailure: Boolean = true
}
