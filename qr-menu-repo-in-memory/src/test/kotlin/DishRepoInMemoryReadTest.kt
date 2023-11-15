import repo.IDishRepository

class DishRepoInMemoryReadTest: RepoDishReadTest() {
    override val repo: IDishRepository = DishRepoInMemory(
        initObjects = initObjects
    )
}
