package com.bguina.trader.domain.usecase.market

import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import io.reactivex.Single

interface IGetExchangeMarketPriceUseCase {
    fun getMarketPrice(
        exchange: EExchange,
        market: EMarket
    ) : Single<Asset>
}
