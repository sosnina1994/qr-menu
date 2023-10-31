import repo.IDishRepository

class DishRepoInMemorySearchTest : RepoDishSearchTest() {
    override val repo: IDishRepository = DishRepoInMemory(
        initObjects = initObjects
    )
}
