package repo

interface IDishRepository {
    suspend fun createDish(rq: DbDishRequest): DbDishResponse
    suspend fun readDish(rq: DbDishIdRequest): DbDishResponse
    suspend fun updateDish(rq: DbDishRequest): DbDishResponse
    suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse
    suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse
}
