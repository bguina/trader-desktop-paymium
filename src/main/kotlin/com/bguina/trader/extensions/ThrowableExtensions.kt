package com.bguina.trader.extensions

import javafx.scene.control.Alert

fun Throwable.showAlertDialog(
    alertType: Alert.AlertType = Alert.AlertType.ERROR
) {
    Alert(alertType).apply {
        title = "Request failure"
        headerText = javaClass.simpleName
        contentText = message
    }.showAndWait()
}
