package repo

import models.QrMenuDish
import models.QrMenuError

data class DbDishesResponse(
    override val data: List<QrMenuDish>?,
    override val isSuccess: Boolean,
    override val errors: List<QrMenuError> = emptyList(),
): IDbResponse<List<QrMenuDish>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbDishesResponse(emptyList(), true)
        fun success(result: List<QrMenuDish>) = DbDishesResponse(result, true)
        fun error(errors: List<QrMenuError>) = DbDishesResponse(null, false, errors)
        fun error(error: QrMenuError) = DbDishesResponse(null, false, listOf(error))
    }
}
