
class DishRepoInMemoryCreateTest : RepoDishCreateTest() {
    override val repo = DishRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
