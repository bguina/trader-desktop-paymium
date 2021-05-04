package com.bguina.trader.domain.model

enum class EExchange(
    val markets: Map<EMarket, MarketFees>,
) {
    PAYMIUM(
        mapOf(
            // Reference: https://www.paymium.com/page/help/fees
            EMarket.BTC_EUR to MarketFees(
                makerFeePercent = -.1,
                takerFeePercent = +.5
            )
        )
    ),
    ;

}
