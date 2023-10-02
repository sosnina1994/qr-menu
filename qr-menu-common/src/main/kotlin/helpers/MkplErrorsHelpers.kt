package helpers

import QrMenuContext
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
