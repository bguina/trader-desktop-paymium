package com.bguina.trader.ui.account_assets

import com.bguina.trader.TraderApp
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EAsset
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import java.io.InputStream
import java.net.URL
import java.util.*

class AccountAssetCell : ListCell<Asset>(), Initializable {
    @FXML
    lateinit var root: HBox

    @FXML
    lateinit var icon: ImageView

    @FXML
    lateinit var amount: Label

    private var model: Asset? = null

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        graphic = root
    }

    override fun updateItem(
        item: Asset?,
        empty: Boolean
    ) {
        super.updateItem(item, empty)

        root.childrenUnmodifiable.forEach { c -> c.isVisible = !empty }

        if (!empty && item != null && item != model) {
            val assetRes = item.currency.iconInputStream ?: defaultIconInputStream
            icon.image = Image(assetRes)
            amount.text = item.toString()
        }

        // keep a reference to the model item
        model = item
    }

    private val EAsset.iconInputStream: InputStream?
        get() = when (this) {
            EAsset.EURO -> "ui/ic_eur.png"
            EAsset.USD -> "ui/ic_usd.png"
            EAsset.BITCOIN -> "ui/ic_btc.png"
        }.run { TraderApp::class.java.getResourceAsStream(this) }

    private val defaultIconInputStream: InputStream?
        get() = TraderApp::class.java.getResourceAsStream("ui/ic_app.png")

    companion object {
        private const val RESOURCE_NAME: String = "account_asset_cell.fxml"
        private val RESOURCE: URL? = AccountAssetCell::class.java.getResource(RESOURCE_NAME)

        fun newInstance(
        ): AccountAssetCell? = FXMLLoader(RESOURCE).run {
            try {
                load<Any>()
                getController<AccountAssetCell>()
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
    }
}
