package com.bguina.trader.data.trader

import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.data.trader.model.EMarketEntity

data class TradeEntity(
   val market: EMarketEntity?,
   val from: AssetEntity?,
   val to: AssetEntity?
)
