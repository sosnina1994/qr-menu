package models

@JvmInline
value class QrMenuRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QrMenuRequestId("")
    }
}