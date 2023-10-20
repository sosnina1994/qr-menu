import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider

data class QrMenuCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = QrMenuCorSettings()
    }
}
