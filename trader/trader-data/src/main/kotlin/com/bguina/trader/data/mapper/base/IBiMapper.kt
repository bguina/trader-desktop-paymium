package com.bguina.trader.data.mapper.base

interface IBiMapper<STRUCT_A, STRUCT_B> : IMapper<STRUCT_A, STRUCT_B> {
    fun reverseMap(
        value: STRUCT_B
    ): STRUCT_A
}
