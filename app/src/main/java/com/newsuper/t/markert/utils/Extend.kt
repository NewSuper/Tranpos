package com.newsuper.t.markert.utils


fun <T> Collection<T>.isListEmpty() : Boolean{
    return this == null || this.isEmpty()
}
