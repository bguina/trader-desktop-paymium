package com.bguina.trader.data.mapper.base

interface IMapper<INPUT, OUTPUT> {
    fun map(
        value: INPUT
    ): OUTPUT
}
