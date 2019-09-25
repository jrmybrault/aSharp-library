package com.jbr.asharplibrary.sharedfoundation

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<List<T>>.plusAssign(newValues: List<T>) {
    val updatedValues = mutableListOf<T>()

    val currentValue = this.value
    if (currentValue != null) {
        updatedValues.addAll(currentValue)
    }

    updatedValues.addAll(newValues)

    this.value = updatedValues
}
