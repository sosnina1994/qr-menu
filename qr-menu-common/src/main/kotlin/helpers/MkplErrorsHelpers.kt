package helpers

import QrMenuContext
import models.EQrMenuState
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