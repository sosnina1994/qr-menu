import repo.IDishRepository

class DishRepoInMemoryDeleteTest : RepoDishDeleteTest() {
    override val repo: IDishRepository = DishRepoInMemory(
        initObjects = initObjects
    )
}
