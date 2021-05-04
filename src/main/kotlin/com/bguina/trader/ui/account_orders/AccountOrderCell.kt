package com.bguina.trader.ui.account_orders

import com.bguina.trader.domain.model.ExchangeOrder
import com.bguina.trader.util.extensions.format
import com.bguina.trader.util.extensions.isoLocalDateTimeFormat
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.GridPane
import java.net.URL
import java.util.*

class AccountOrderCell : ListCell<ExchangeOrder>(), Initializable {
    @FXML
    lateinit var root: GridPane

    @FXML
    lateinit var status: Label

    @FXML
    lateinit var price: Label

    @FXML
    lateinit var executedTrade: Label

    private var model: ExchangeOrder? = null

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        // add a un-focused listener to each child-item that triggers commitEdit(...)
        root.childrenUnmodifiable.forEach { c ->
            c.focusedProperty().addListener { obj, prev, curr ->
                if (!curr) {
                    commitEdit(model)
                }
            }
        }

        graphic = root
    }

    override fun updateItem(
        item: ExchangeOrder?,
        empty: Boolean
    ) {
        super.updateItem(item, empty)

        root.childrenUnmodifiable.forEach { c -> c.isVisible = !empty }

        if (!empty && item != null && item != model) {
            status.text = item.status.name + " at " + item.updatedAt.isoLocalDateTimeFormat
            price.text = item.expectedTrade.price.toString()
            val percent = item.executionPercent.format(2).plus('%')
            executedTrade.text = "[$percent] ${item.executedTrade.from} -> ${item.executedTrade.to} ${item.cumulatedFees?.format(true)}"
        }

        model = item
    }

    companion object {
        private const val RESOURCE_NAME :String = "account_order_cell.fxml"
        private val RESOURCE : URL? = AccountOrderCell::class.java.getResource(RESOURCE_NAME)

        fun newInstance(
        ): AccountOrderCell? = FXMLLoader(RESOURCE).run {
            try {
                load<Any>()
                getController<AccountOrderCell>()
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
    }
}
