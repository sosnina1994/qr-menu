package helpers

import QrMenuContext
import exceptions.RepoConcurrencyException
import models.EQrMenuState
import models.QrMenuDishLock
import models.QrMenuError

fun Throwable.asQrMenuError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = QrMenuError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun QrMenuContext.addError(vararg error: QrMenuError) = errors.addAll(error)

fun QrMenuContext.fail(error: QrMenuError) {
    addError(error)
    state = EQrMenuState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: QrMenuError.Level = QrMenuError.Level.ERROR,
) = QrMenuError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level
)

fun errorRepoConcurrency(
    expectedLock: QrMenuDishLock,
    actualLock: QrMenuDishLock?,
    exception: Exception? = null,
) = QrMenuError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = QrMenuError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = QrMenuError(
    field = "id",
    message = "Id must not be null or blank"
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: QrMenuError.Level = QrMenuError.Level.ERROR,
) = QrMenuError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)
