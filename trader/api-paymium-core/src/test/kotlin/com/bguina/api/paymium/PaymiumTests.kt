package com.bguina.api.paymium

import com.bguina.api.paymium.model.v1.EPaymiumV1Currency
import com.bguina.api.paymium.model.v1.EPaymiumV1Interval
import com.bguina.api.paymium.model.v1.data.currency.ohlcv.*
import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import org.apache.logging.log4j.Logger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class PaymiumTests(
    private val userTestImpl: IPaymium,
    private val logger: Logger
) {
    private val testAccountEmail: String =
        LocalProperties.getProperty(LocalProperties.LOCAL_PROPERTIES_PROP_PAYMIUM_EMAIL)

    @Test
    fun shouldListCountries() {
        userTestImpl.listAcceptedCountries { countryDtos ->
            countryDtos.forEach {
//                logger.info("country is $it")
                assertEquals(true, it.accepted)
                assertNotNull(it.card_partner_accepted)
                assertNotNull(it.iso_alpha2)
                assertNotNull(it.iso_alpha3)
                assertNotNull(it.name_de)
                assertNotNull(it.name_en)
                assertNotNull(it.name_es)
                assertNotNull(it.name_fr)
                assertNotNull(it.name_it)
            }
        }
    }


    @Test
    fun shouldGetBitcoinOhlcvInEuro() {
        userTestImpl.getCurrencyOhlcv(
            currency = EPaymiumV1Currency.EUR,
//            startTimeEpoch = 0,
            endTimeEpoch = System.currentTimeMillis(),
            interval = EPaymiumV1Interval.ONE_DAY
        ) { ohlcvDto ->
//            logger.info("ohlcv is ${ohlcvDto.size}-entries long")
            ohlcvDto.forEach {
                assertEquals(6, it.size)
                assertNotNull(it.timestamp)
                assertNotNull(it.open)
                assertNotNull(it.high)
                assertNotNull(it.low)
                assertNotNull(it.close)
                assertNotNull(it.volume)
            }
        }
    }

    @Test
    fun shouldGetBitcoinTickerInEuro() {
        userTestImpl.getBitcoinTickerInEuro { btcTicker ->
//            logger.info("value is $btcTicker")
            assertNotNull(btcTicker.ask)
            assertNotNull(btcTicker.at)
            assertNotNull(btcTicker.bid)
            assertNotNull(btcTicker.currency)
            assertNotNull(btcTicker.high)
            assertNotNull(btcTicker.low)
            assertNotNull(btcTicker.midpoint)
            assertNotNull(btcTicker.price)
            assertNotNull(btcTicker.variation)
            assertNotNull(btcTicker.volume)
        }
    }

    @Test
    fun shouldListLatestTrades() {
        userTestImpl.listLatestTrades(
            currency = EPaymiumV1Currency.EUR,
            sinceEpoch = 0
        ) { latestTradeListDto ->
            latestTradeListDto.forEach {
//                logger.info("latest trade is $it")
                assertNotNull(it.created_at)
                assertNotNull(it.currency)
                assertNotNull(it.created_at)
                assertNotNull(it.price)
            }
        }
    }
    @Test
    fun shouldGetBitcoinMarketDepth() {
        userTestImpl.getMarketDepth(
            currency = EPaymiumV1Currency.BTC
        ) { marketDepthDto ->
//            logger.info("market depth is $marketDepthDto")
            assertNotNull(marketDepthDto.asks)
            assertNotNull(marketDepthDto.bids)
        }
    }

    @Test
    fun shouldGetUser() {
        userTestImpl.getUser { user ->
//            logger.info("User is $user")
            assertNotNull(user.balance_btc)
            assertNotNull(user.balance_eur)
            assertNotNull(user.channel_id)
            assertNotNull(user.email)
            assertEquals(testAccountEmail, user.email)
            assertNotNull(user.locale)
            assertNotNull(user.locked_btc)
            assertNotNull(user.locked_eur)
            assertNotNull(user.meta_state)
            assertEquals(
                user.meta_state == PaymiumUserDTO.META_STATE_APPROVED,
                user.isApproved
            )
            assertNotNull(user.name)
        }
    }

    @Test
    fun shouldListUserBitcoinDepositAddresses() {
        userTestImpl.listUserBitcoinDepositAddresses { depositAddressDtoList ->
            assertEquals(2, depositAddressDtoList.size)
            depositAddressDtoList.forEach {
//                logger.info("deposit address is $it")
                assertNotNull(it.address)
                assertEquals(EPaymiumV1Currency.BTC.identifier.uppercase(), it.currency)
                assertNotNull(it.valid_until)
            }
        }
    }

    @Test
    fun shouldListUserOrders() {
        userTestImpl.listUserOrders(
            limit = 50,
            offset = 0
        ) { userOrderListDto ->
            userOrderListDto.forEach {
//                logger.info("user orders is $it")
                assertNotNull(it.amount)
                assertNotNull(it.btc_fee)
                assertNotNull(it.created_at)
                assertNotNull(it.currency)
                assertNotNull(it.created_at)
                assertNotNull(it.currency_fee)
                assertNotNull(it.direction)
                assertNotNull(it.price)
                assertNotNull(it.state)
            }
        }
    }

}
