package models

data class QrMenuError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
    val level: Level = Level.ERROR,
) {
    enum class Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}



