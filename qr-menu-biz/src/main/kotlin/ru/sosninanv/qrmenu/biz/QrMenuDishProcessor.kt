package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuCorSettings
import models.EQrMenuCommand
import models.QrMenuDishId
import ru.sosninanv.qrmenu.biz.groups.operation
import ru.sosninanv.qrmenu.biz.groups.stubs
import ru.sosninanv.qrmenu.biz.validation.*
import ru.sosninanv.qrmenu.biz.workers.*
import ru.sosninanv.qrmenu.cor.handlers.worker
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

                validation {
                    worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                    worker("Очистка id") { dishValidating.id = QrMenuDishId.NONE }
                    worker("Очистка имени") { dishValidating.name = dishValidating.name.trim() }

                    validateNameNotEmpty("Проверка, что имя не пустое")
                    validateNameHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")
                    finishAdValidation("Завершение проверок")

                }
            }

            operation("Получение блюда", EQrMenuCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                    worker("Очистка id") { dishValidating.id = QrMenuDishId(dishValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Изменение блюда", EQrMenuCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                worker("Очистка id") { dishValidating.id = QrMenuDishId(dishValidating.id.asString().trim()) }
                worker("Очистка имени") { dishValidating.name = dishValidating.name.trim() }
                worker("Очистка описания") { dishValidating.description = dishValidating.description.trim() }

                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateNameNotEmpty("Проверка на непустое имя")
                validateNameHasContent("Проверка символов")
                validateDescriptionNotEmpty("Проверка на непустое описание")
                validateDescriptionHasContent("ППроверка символов")

                finishAdValidation("Успешное завершение процедуры валидации")
            }

            operation("Удаление блюда", EQrMenuCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                    worker("Очистка id") { dishValidating.id = QrMenuDishId(dishValidating.id.asString().trim()) }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Поиск блюда", EQrMenuCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в adFilterValidating") { dishFilterValidating = dishFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
            }

        }.build()
    }
}