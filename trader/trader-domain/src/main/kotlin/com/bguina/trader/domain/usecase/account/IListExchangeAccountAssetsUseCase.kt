package com.bguina.trader.domain.usecase.account

import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import io.reactivex.Single

interface IListExchangeAccountAssetsUseCase {
    fun listAssets(
        exchange: EExchange
    ) : Single<List<Asset>>
}
