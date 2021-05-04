package com.bguina.trader.data.remote.exchange.paymium.mapper

import com.bguina.api.paymium.model.v1.user.orders.PaymiumOrderOperationDTO
import com.bguina.trader.data.mapper.ExchangeOrderOperationEntity
import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.data.trader.model.EAssetEntity
import javax.inject.Inject

class PaymiumOrderOperationMapper @Inject constructor(
    private val currencyDtoMapper: IBiMapper<String, EAssetEntity>
) : ABiMapper<PaymiumOrderOperationDTO, ExchangeOrderOperationEntity>(
    PaymiumOrderOperationDTO::class,
    ExchangeOrderOperationEntity::class
) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: PaymiumOrderOperationDTO
        ): ExchangeOrderOperationEntity = ExchangeOrderOperationEntity(
            createdAt = value.created_at,
            isFee = value.isFee,
            asset = AssetEntity(
                amount = value.amount?.toDouble(),
                currency = value.currency?.run(currencyDtoMapper::map)
            )
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeOrderOperationEntity
        ): PaymiumOrderOperationDTO = PaymiumOrderOperationDTO(
            created_at = value.createdAt,
            amount = value.asset?.amount.toString(),
            currency = value.asset?.currency?.run(currencyDtoMapper::reverseMap)
        )
    }

    private val PaymiumOrderOperationDTO.isLockUnlockOp: Boolean
        get() = name.equals(PaymiumOrderOperationDTO.NAME_LOCK) ||
                name.equals(PaymiumOrderOperationDTO.NAME_UNLOCK)

    private val PaymiumOrderOperationDTO.isFee: Boolean
        get() = name.equals(PaymiumOrderOperationDTO.NAME_BTC_PURCHASE_FEE) ||
                name.equals(PaymiumOrderOperationDTO.NAME_BTC_PURCHASE_FEE_INCENTIVE) ||
                name.equals(PaymiumOrderOperationDTO.NAME_BTC_SALE_FEE) ||
                name.equals(PaymiumOrderOperationDTO.NAME_BTC_SALE_FEE_INCENTIVE)

}
