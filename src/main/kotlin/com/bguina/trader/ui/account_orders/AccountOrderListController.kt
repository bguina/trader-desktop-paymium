package com.bguina.trader.ui.account_orders

import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.ExchangeOrder
import com.bguina.trader.domain.usecase.order.IListExchangeAccountOrdersUseCase
import com.bguina.trader.extensions.rx2.every
import com.bguina.trader.extensions.showAlertDialog
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

class AccountOrderListController @Inject constructor(
    private val logger: Logger,
    private val getExchangeOrderHistoryUseCase: IListExchangeAccountOrdersUseCase
) : Initializable {

    @FXML
    lateinit var orderListView: ListView<ExchangeOrder>

    private val orderObservableList: ObservableList<ExchangeOrder> = FXCollections.observableArrayList()

    private val orderCellFactory: Callback<ListView<ExchangeOrder>, ListCell<ExchangeOrder>> = Callback {
        AccountOrderCell.newInstance()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        orderListView.cellFactory = orderCellFactory
        orderListView.setOnScroll { scroll ->
            // todo: auto-loading of the next page
        }
        orderListView.items = orderObservableList

        observe()
    }

    private fun observe() {
        getExchangeOrderHistoryUseCase.getExchangeUserOrders(
            exchange = EExchange.PAYMIUM
        )
            .every(10, TimeUnit.SECONDS)
            .doOnNext { silentFailure = true }
            .subscribe(orderObservableList::setAll) {
                logger.warn("${it.javaClass.simpleName}: ${it.message.orEmpty()}", it)
                if (!silentFailure) it.showAlertDialog()
                silentFailure = false
                observe()
            }
    }

    private var silentFailure: Boolean = true
}
