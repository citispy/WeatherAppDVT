package com.mobile.weatherappdvt.util

class Event<T>(private val content: T) {
    // Allow external read but not write
    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    val contentIfNotHandled: T?
        get() {
            if (hasBeenHandled) {
                return null
            }
            hasBeenHandled = true
            return content
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T {
        return content
    }
}