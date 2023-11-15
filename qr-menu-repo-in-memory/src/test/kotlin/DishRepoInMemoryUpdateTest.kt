import repo.IDishRepository

class DishRepoInMemoryUpdateTest : RepoDishUpdateTest() {
    override val repo: IDishRepository = DishRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
