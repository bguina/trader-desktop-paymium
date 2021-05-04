package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.data.trader.model.EAssetEntity
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EAsset
import javax.inject.Inject

class AssetMapper @Inject constructor(
    private val currencyMapper: IBiMapper<EAssetEntity, EAsset>
) : ABiMapper<AssetEntity, Asset>(AssetEntity::class, Asset::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: AssetEntity
        ): Asset = Asset(
            amount = value.amount ?: throwMissingFieldException("amount"),
            currency = value.currency?.run(currencyMapper::map) ?: throwMissingFieldException("currency")
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: Asset
        ): AssetEntity = AssetEntity(
            amount = value.amount,
            currency = value.currency.run(currencyMapper::reverseMap)
        )
    }
}
