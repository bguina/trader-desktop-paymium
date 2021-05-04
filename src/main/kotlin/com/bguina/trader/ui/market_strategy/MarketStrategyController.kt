package com.bguina.trader.ui.market_strategy

import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.usecase.strategy.IRunAutoRaiseStrategyUseCase
import javafx.fxml.Initializable
import org.apache.logging.log4j.Logger
import java.net.URL
import java.util.*
import javax.inject.Inject

class MarketStrategyController @Inject constructor(
    private val logger: Logger,
    private val runAutoRaiseStrategyUseCase: IRunAutoRaiseStrategyUseCase
) : Initializable {

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        if (false) {
            runAutoRaiseStrategyUseCase.runAutoRaise(
                exchange = EExchange.PAYMIUM,
                market = EMarket.BTC_EUR,
                direction = EMarket.EDirection.BUY,
                initialOffer = TODO(),
                limit = TODO()
            )
        }
    }
}
