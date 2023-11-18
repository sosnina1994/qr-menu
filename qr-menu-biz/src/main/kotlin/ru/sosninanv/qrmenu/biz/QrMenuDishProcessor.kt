package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuCorSettings
import models.EQrMenuCommand
import models.EQrMenuState
import models.QrMenuDishId
import models.QrMenuDishLock
import ru.sosninanv.qrmenu.biz.general.initRepo
import ru.sosninanv.qrmenu.biz.general.prepareResult
import ru.sosninanv.qrmenu.biz.groups.operation
import ru.sosninanv.qrmenu.biz.groups.stubs
import ru.sosninanv.qrmenu.biz.permission.accessValidation
import ru.sosninanv.qrmenu.biz.permission.chainPermissions
import ru.sosninanv.qrmenu.biz.permission.frontPermissions
import ru.sosninanv.qrmenu.biz.repo.*
import ru.sosninanv.qrmenu.biz.validation.*
import ru.sosninanv.qrmenu.biz.workers.*
import ru.sosninanv.qrmenu.cor.handlers.chain
import ru.sosninanv.qrmenu.cor.handlers.worker
import ru.sosninanv.qrmenu.cor.rootChain

class QrMenuDishProcessor(
    @Suppress("unused")
    private val corSettings: QrMenuCorSettings = QrMenuCorSettings.NONE
) {
    suspend fun exec(ctx: QrMenuContext) = BusinessChain.exec(ctx.apply { settings = corSettings })

    companion object {

        val BusinessChain = rootChain<QrMenuContext> {
            initStatus("Инициализация бизнес-цепочки")
            initRepo("Инициализация репозитория")

            /** CREATE */
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
                    finishDishValidation("Завершение проверок")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    accessValidation("Вычисление прав доступа")
                    repoCreate("Создание объекта в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")

            }

            /** READ */
            operation("Получение объекта", EQrMenuCommand.READ) {
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

                    finishDishValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объекта из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == EQrMenuState.RUNNING }
                        handle { dishRepoDone = dishRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }

            /** UPDATE */
            operation("Изменение данных", EQrMenuCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                    worker("Очистка id") { dishValidating.id = QrMenuDishId(dishValidating.id.asString().trim()) }
                    worker("Очистка lock") { dishValidating.lock = QrMenuDishLock(dishValidating.lock.asString().trim()) }
                    worker("Очистка имени") { dishValidating.name = dishValidating.name.trim() }
                    worker("Очистка описания") { dishValidating.description = dishValidating.description.trim() }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")
                    validateNameNotEmpty("Проверка на непустое имя")
                    validateNameHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка символов")

                    finishDishValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение данных из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление объекта в БД")
                }
                prepareResult("Подготовка ответа")

            }

            /** DELETE */
            operation("Удаление объека", EQrMenuCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в dishValidating") { dishValidating = dishRequest.deepCopy() }
                    worker("Очистка id") { dishValidating.id = QrMenuDishId(dishValidating.id.asString().trim()) }
                    worker("Очистка lock") { dishValidating.lock = QrMenuDishLock(dishValidating.lock.asString().trim()) }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")
                    finishDishValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение объекта из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление объекта из БД")
                }
                prepareResult("Подготовка ответа")
            }

            /** SEARCH */
            operation("Поиск объекта", EQrMenuCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки обращения к базе данных")
                    stubNoCase("Имитация ошибки: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в dishFilterValidating") { dishFilterValidating = dishFilterRequest.copy() }
                    finishDishFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск объектов в БД по фильтру")
                prepareResult("Подготовка ответа")
            }

        }.build()
    }
}