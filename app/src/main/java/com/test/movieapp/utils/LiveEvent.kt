package com.test.movieapp.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Event notification using LiveData
 * https://qiita.com/KazaKago/items/acce0c1a970441b44f39
 */
open class LiveEvent<T> : LiveData<T>() {

    private val dispatchedTagSet = mutableSetOf<String>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observe(owner, System.identityHashCode(observer).toString(), observer)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        observeForever(System.identityHashCode(observer).toString(), observer)
    }

    open fun post(t: T?) {
        dispatchedTagSet.clear()
        postValue(t)
    }

    @MainThread
    protected fun observe(owner: LifecycleOwner, tag: String, observer: Observer<in T>) {
        super.observe(owner, {
            val internalTag = "${owner::class.java.name}#$tag"
            if (!dispatchedTagSet.contains(internalTag)) {
                dispatchedTagSet.add(internalTag)
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    protected fun observeForever(tag: String, observer: Observer<in T>) {
        super.observeForever {
            if (!dispatchedTagSet.contains(tag)) {
                dispatchedTagSet.add(tag)
                observer.onChanged(it)
            }
        }
    }
}
