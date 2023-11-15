package repo

import models.QrMenuError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<QrMenuError>
}
