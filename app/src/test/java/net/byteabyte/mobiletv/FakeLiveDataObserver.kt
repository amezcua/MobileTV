package net.byteabyte.bankstep

import androidx.lifecycle.Observer

class FakeLiveDataObserver<T>: Observer<T> {
    private val observedItems: MutableList<T> = mutableListOf()

    override fun onChanged(item: T) {
        observedItems.add(item)
    }

    fun observerItemsCount(): Int = observedItems.size

    fun lastObservedItem(): T = observedItems.last()

    fun firstObservedItem(): T = observedItems.first()

    fun onlyObservedItem(): T {
        check(observerItemsCount() == 1) { "There are ${observerItemsCount()} items captured." }
        return observedItems.first()
    }

    fun observedItemAt(position: Int): T = observedItems[position]

    fun allObserved(): List<T> = observedItems
}