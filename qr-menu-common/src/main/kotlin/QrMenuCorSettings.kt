import repo.IDishRepository
import ru.sosninanv.qrmenu.logging.MpLoggerProvider

data class QrMenuCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val repoStub: IDishRepository = IDishRepository.NONE,
    val repoTest: IDishRepository = IDishRepository.NONE,
    val repoProd: IDishRepository = IDishRepository.NONE,
) {
    companion object {
        val NONE = QrMenuCorSettings()
    }
}
