import ru.sosninanv.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = DishCreateResponse(
        requestId = "123",
        dish = DishResponseObject(
            name = "dish name",
            description = "dish description",
            cost = 10.00,
            dishType = EDishType.DESERT,
            visibility = EDishVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"name\":\\s*\"dish name\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as DishCreateResponse

        assertEquals(response, obj)
    }
}
