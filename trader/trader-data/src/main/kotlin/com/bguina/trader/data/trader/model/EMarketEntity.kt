package com.bguina.trader.data.trader.model

enum class EMarketEntity(
    val stock: EAssetEntity,
    val currency: EAssetEntity,
){
    BTC_EUR(EAssetEntity.BITCOIN, EAssetEntity.EURO),
    BTC_USD(EAssetEntity.BITCOIN, EAssetEntity.USD),
}
