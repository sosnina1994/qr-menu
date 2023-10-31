package repo

import helpers.errorRepoConcurrency
import models.QrMenuDish
import models.QrMenuDishLock
import models.QrMenuError
import helpers.errorEmptyId as qrMenuErrorEmptyId
import helpers.errorNotFound as qrMenuErrorNotFound

data class DbDishResponse(
    override val data: QrMenuDish?,
    override val isSuccess: Boolean,
    override val errors: List<QrMenuError> = emptyList()
): IDbResponse<QrMenuDish> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbDishResponse(null, true)
        fun success(result: QrMenuDish) = DbDishResponse(result, true)
        fun error(errors: List<QrMenuError>, data: QrMenuDish? = null) = DbDishResponse(data, false, errors)
        fun error(error: QrMenuError, data: QrMenuDish? = null) = DbDishResponse(data, false, listOf(error))

        val errorEmptyId = error(qrMenuErrorEmptyId)
        fun errorConcurrent(lock: QrMenuDishLock, dish: QrMenuDish?) = error(
            errorRepoConcurrency(lock, dish?.lock?.let { QrMenuDishLock(it.asString()) }),
            dish
        )
        val errorNotFound = error(qrMenuErrorNotFound)

    }
}
