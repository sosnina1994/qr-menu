package models

@JvmInline
value class QrMenuDishId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = QrMenuDishId("")
    }
}