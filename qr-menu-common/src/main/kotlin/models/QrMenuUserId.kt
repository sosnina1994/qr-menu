package models

import kotlin.jvm.JvmInline

@JvmInline
value class QrMenuUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QrMenuUserId("")
    }
}
