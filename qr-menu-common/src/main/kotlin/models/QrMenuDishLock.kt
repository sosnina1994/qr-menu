package models

import kotlin.jvm.JvmInline

@JvmInline
value class QrMenuDishLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QrMenuDishLock("")
    }
}
