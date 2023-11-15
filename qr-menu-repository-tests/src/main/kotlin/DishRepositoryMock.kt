import repo.*

class DishRepositoryMock(
    private val invokeCreateDish: (DbDishRequest) -> DbDishResponse = { DbDishResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadDish: (DbDishIdRequest) -> DbDishResponse = { DbDishResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateDish: (DbDishRequest) -> DbDishResponse = { DbDishResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteDish: (DbDishIdRequest) -> DbDishResponse = { DbDishResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchDish: (DbDishFilterRequest) -> DbDishesResponse = { DbDishesResponse.MOCK_SUCCESS_EMPTY },
): IDishRepository {
    override suspend fun createDish(rq: DbDishRequest): DbDishResponse {
        return invokeCreateDish(rq)
    }

    override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse {
        return invokeReadDish(rq)
    }

    override suspend fun updateDish(rq: DbDishRequest): DbDishResponse {
        return invokeUpdateDish(rq)
    }

    override suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse {
        return invokeDeleteDish(rq)
    }

    override suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse {
        return invokeSearchDish(rq)
    }
}
