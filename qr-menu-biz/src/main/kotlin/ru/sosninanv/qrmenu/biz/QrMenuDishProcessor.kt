package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuCorSettings
import models.EQrMenuCommand
import ru.sosninanv.qrmenu.biz.groups.operation
import ru.sosninanv.qrmenu.biz.groups.stubs
import ru.sosninanv.qrmenu.biz.workers.*
import ru.sosninanv.qrmenu.cor.rootChain

class QrMenuDishProcessor {
    @Suppress("unused")
    private val corSettings: QrMenuCorSettings = QrMenuCorSettings.NONE

    suspend fun exec(ctx: QrMenuContext) = BusinessChain.exec(ctx)

    companion object {

        val BusinessChain = rootChain<QrMenuContext> {
            initStatus("Инициализация бизнес-цепочки")

            operation("Создание блюда", EQrMenuCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadName("Имитация ошибки валидации имени")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
            }

            operation("Получение блюда", EQrMenuCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
            }

            operation("Изменение блюда", EQrMenuCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
            }

            operation("Удаление блюда", EQrMenuCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
            }

            operation("Поиск блюда", EQrMenuCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
            }

        }.build()
    }
}