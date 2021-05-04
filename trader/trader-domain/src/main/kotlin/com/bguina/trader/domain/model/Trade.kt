package com.bguina.trader.domain.model

data class Trade(
    val market: EMarket,
    val from: Asset,
    val to: Asset
) {
    val stockAsset: Asset
        get() = listOf(from, to).single { it.currency == market.stock }

    val currencyAsset: Asset
        get() = listOf(from, to).single { it.currency == market.currency }

    val price: Asset
        get() = Asset(
            amount = currencyAsset.amount / stockAsset.amount,
            currency = market.currency
        )
}
