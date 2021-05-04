package com.bguina.trader.data.mapper.base

import kotlin.reflect.KClass

abstract class AMapper<STRUCT_A : Any, STRUCT_B : Any>(
    fromClass: KClass<out STRUCT_A>,
    toClass: KClass<out STRUCT_B>
) : IMapper<STRUCT_A, STRUCT_B> {
    protected val fromClassName: String = fromClass.java.simpleName
    protected val toClassName: String = toClass.java.simpleName

    @Throws(NoSuchFieldException::class)
    fun throwMissingFieldException(
        field: String
    ): Nothing = throw NoSuchFieldException(fromClassName.plus('.').plus(field))
}
