import models.EQrMenuDishType
import repo.*

class DishRepoStub() : IDishRepository {

    override suspend fun createDish(rq: DbDishRequest): DbDishResponse {
        return DbDishResponse(
            data = QrMenuDishStub.prepareResult {  },
            isSuccess = true
        )
    }

    override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse {
        return DbDishResponse(
            data = QrMenuDishStub.prepareResult {  },
            isSuccess = true
        )
    }

    override suspend fun updateDish(rq: DbDishRequest): DbDishResponse {
        return DbDishResponse(
            data = QrMenuDishStub.prepareResult {  },
            isSuccess = true
        )
    }

    override suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse {
        return DbDishResponse(
            data = QrMenuDishStub.prepareResult {  },
            isSuccess = true
        )
    }

    override suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse {
        return DbDishesResponse(
            data = QrMenuDishStub.prepareSearchList("", EQrMenuDishType.DESSERT),
            isSuccess = true
        )
    }
}
