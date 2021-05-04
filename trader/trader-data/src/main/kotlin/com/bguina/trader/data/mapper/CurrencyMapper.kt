package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.trader.model.EAssetEntity
import com.bguina.trader.domain.model.EAsset
import javax.inject.Inject

class CurrencyMapper @Inject constructor(
) : ABiMapper<EAssetEntity, EAsset>(EAssetEntity::class, EAsset::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: EAssetEntity
        ): EAsset = when (value) {
            EAssetEntity.EURO ->  EAsset.EURO
            EAssetEntity.USD -> EAsset.USD
            EAssetEntity.BITCOIN ->  EAsset.BITCOIN
        }
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: EAsset
        ): EAssetEntity = when (value) {
            EAsset.EURO ->  EAssetEntity.EURO
            EAsset.USD -> EAssetEntity.USD
            EAsset.BITCOIN ->  EAssetEntity.BITCOIN
        }
    }
}
