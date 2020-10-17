package com.transpos.sale.utils

import java.util.*

fun <T> Collection<T>.isListEmpty() : Boolean{
    return this == null || this.isEmpty()
}
