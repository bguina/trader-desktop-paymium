package com.bguina.trader.ui.account_assets

import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.usecase.account.IListExchangeAccountAssetsUseCase
import com.bguina.trader.extensions.rx2.every
import com.bguina.trader.extensions.showAlertDialog
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback
import org.apache.logging.log4j.Logger
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AccountAssetListController @Inject constructor(
    private val logger: Logger,
    private val listUserAssetsUseCase: IListExchangeAccountAssetsUseCase
) : Initializable {

    @FXML
    lateinit var assetListView: ListView<Asset>

    private val assetObservableList: ObservableList<Asset> = FXCollections.observableArrayList()

    private val assetCellFactory: Callback<ListView<Asset>, ListCell<Asset>> = Callback {
        AccountAssetCell.newInstance()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        assetListView.cellFactory = assetCellFactory
        assetListView.items = assetObservableList
        assetListView.isMouseTransparent = true
        assetListView.isFocusTraversable = false
        assetListView.prefHeightProperty().bind(Bindings.size(assetObservableList).multiply(36))
        observe()
    }

    private fun observe() {
        listUserAssetsUseCase.listAssets(
            exchange = EExchange.PAYMIUM
        )
            .every(10, TimeUnit.SECONDS)
            .doOnNext { silentFailure = true }
            .subscribe(assetObservableList::setAll) {
                logger.warn("${it.javaClass.simpleName}: ${it.message.orEmpty()}", it)
                if (!silentFailure) it.showAlertDialog()
                silentFailure = false
                observe()
            }
    }

    private var silentFailure: Boolean = true

}
