package com.bguina.trader.data.mapper.base

import kotlin.reflect.KClass

abstract class ABiMapper<STRUCT_A : Any, STRUCT_B : Any>(
    private val fromClass: KClass<out STRUCT_A>,
    private val toClass: KClass<out STRUCT_B>
) : IBiMapper<STRUCT_A, STRUCT_B> {
    abstract val mapper: AInnerMapper
    abstract val reverseMapper: AInnerReverseMapper

    protected val fromClassName: String = fromClass.java.simpleName
    protected val toClassName: String = toClass.java.simpleName

    final override fun map(
        value: STRUCT_A
    ): STRUCT_B = mapper.map(value)

    final override fun reverseMap(
        value: STRUCT_B
    ): STRUCT_A = reverseMapper.map(value)

    abstract inner class AInnerMapper : AMapper<STRUCT_A, STRUCT_B>(fromClass, toClass)

    abstract inner class AInnerReverseMapper : AMapper<STRUCT_B, STRUCT_A>(toClass, fromClass)
}
