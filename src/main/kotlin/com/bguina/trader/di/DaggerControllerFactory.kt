package com.bguina.trader.di

import javafx.util.Callback
import javax.inject.Inject
import javax.inject.Provider

class DaggerControllerFactory @Inject constructor(
    private val controllerProviders: Map<Class<out Any>, @JvmSuppressWildcards Provider<Any>>
) : Callback<Class<*>, Any> {

    override fun call(
        param: Class<*>
    ): Any = controllerProviders[param]?.get()
        ?: throw IllegalArgumentException("Could not inject class ${param.simpleName}")

}
