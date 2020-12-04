package com.newsuper.t.sale.utils

fun <T> Collection<T>.isListEmpty() : Boolean{
    return this == null || this.isEmpty()
}
