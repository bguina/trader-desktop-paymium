package com.bguina.trader.data.remote.exchange.paymium.mapper

import com.bguina.api.paymium.model.v1.EPaymiumV1Currency
import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.trader.model.EAssetEntity
import javax.inject.Inject

class PaymiumCurrencyMapper @Inject constructor(
) : ABiMapper<String, EAssetEntity>(String::class, EAssetEntity::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: String
        ): EAssetEntity= when {
            value.equals(EPaymiumV1Currency.BTC.identifier, true) -> EAssetEntity.BITCOIN
            value.equals(EPaymiumV1Currency.EUR.identifier, true) -> EAssetEntity.EURO
            else -> throw NotImplementedError("unknown Paymium currency $value")
        }
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: EAssetEntity
        ): String = when (value) {
            EAssetEntity.BITCOIN-> EPaymiumV1Currency.BTC.identifier
            EAssetEntity.USD -> throw NotImplementedError("unknown Paymium currency $value")
            EAssetEntity.EURO-> EPaymiumV1Currency.EUR.identifier
        }
    }
}
