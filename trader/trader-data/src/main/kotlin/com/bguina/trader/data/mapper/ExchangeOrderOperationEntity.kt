package com.bguina.trader.data.mapper

import com.bguina.trader.data.trader.model.AssetEntity
import java.util.*

data class ExchangeOrderOperationEntity(
    val isFee: Boolean?,
    val createdAt: Date?,
    val asset: AssetEntity?
)
