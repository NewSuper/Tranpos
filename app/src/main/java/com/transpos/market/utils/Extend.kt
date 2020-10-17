package com.transpos.sale.utils


fun <T> Collection<T>.isListEmpty() : Boolean{
    return this == null || this.isEmpty()
}
