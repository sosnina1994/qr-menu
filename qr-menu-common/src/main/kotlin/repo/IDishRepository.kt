package repo

interface IDishRepository {
    suspend fun createDish(rq: DbDishRequest): DbDishResponse
    suspend fun readDish(rq: DbDishIdRequest): DbDishResponse
    suspend fun updateDish(rq: DbDishRequest): DbDishResponse
    suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse
    suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse

    companion object {
        val NONE = object : IDishRepository {
            override suspend fun createDish(rq: DbDishRequest): DbDishResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateDish(rq: DbDishRequest): DbDishResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse {
                TODO("Not yet implemented")
            }

        }
    }
}
