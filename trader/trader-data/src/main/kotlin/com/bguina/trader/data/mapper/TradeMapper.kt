package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.TradeEntity
import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.data.trader.model.EMarketEntity
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.model.Trade
import javax.inject.Inject

class TradeMapper @Inject constructor(
    private val marketMapper: IBiMapper<EMarketEntity, EMarket>,
    private val assetMapper: IBiMapper<AssetEntity, Asset>,
) : ABiMapper<TradeEntity, Trade>(TradeEntity::class, Trade::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: TradeEntity
        ): Trade = Trade(
            market = value.market?.run(marketMapper::map) ?: throwMissingFieldException("market"),
            from = value.from?.run(assetMapper::map) ?: throwMissingFieldException("from"),
            to = value.to?.run(assetMapper::map) ?: throwMissingFieldException("to"),
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: Trade
        ): TradeEntity = TradeEntity(
            market = value.market.run(marketMapper::reverseMap),
            from = value.from.run(assetMapper::reverseMap),
            to = value.to.run(assetMapper::reverseMap),
        )
    }
}
