package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.ExchangeOrderOperation
import javax.inject.Inject

class ExchangeOrderOperationMapper @Inject constructor(
    private val assetMapper: IBiMapper<AssetEntity, Asset>
) : ABiMapper<ExchangeOrderOperationEntity, ExchangeOrderOperation>(
    ExchangeOrderOperationEntity::class,
    ExchangeOrderOperation::class
) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeOrderOperationEntity
        ): ExchangeOrderOperation = ExchangeOrderOperation(
            createdAt = value.createdAt ?: throwMissingFieldException("createdAt"),
            isFee = value.isFee ?: throwMissingFieldException("isFee"),
            asset = value.asset?.run(assetMapper::map) ?: throwMissingFieldException("asset")
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeOrderOperation
        ): ExchangeOrderOperationEntity = ExchangeOrderOperationEntity(
            createdAt = value.createdAt,
            isFee = value.isFee,
            asset = value.asset.run(assetMapper::reverseMap)
        )
    }
}
