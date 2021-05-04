package com.bguina.trader.domain.model

enum class EMarket(
    val stock: EAsset,
    val currency: EAsset,
){
    BTC_EUR(EAsset.BITCOIN, EAsset.EURO),
    BTC_USD(EAsset.BITCOIN, EAsset.USD),
    ;

    val shortName: String
        get() = name.replace("_", "")

    enum class EDirection {
        SELL,
        BUY
    }
}
