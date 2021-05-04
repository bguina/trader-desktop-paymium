package com.bguina.trader.domain.model

/**
Maker-taker fees, also known as payment for order flow, provides liquidity providers with rebates for participating in markets.
Makers refers to market makers who provide two-sided markets, and takers as those trading the prices set by market makers.
When you create an order that is immediately matched with already existing orders, you're a taker because you take liquidity from the market.
When you add an order that doesn't match existing offers, you add liquidity to the market and are charged a maker fee.
 * */
data class MarketFees(
    // Maker fees are paid when you add liquidity to our order book by placing a limit order below the ticker price for buy, and above the ticker price for sell.
    val makerFeePercent: Double,
    // Taker fees are paid when you remove liquidity from our order book by placing any order that is executed against an order on the order book.
    val takerFeePercent: Double,
)
