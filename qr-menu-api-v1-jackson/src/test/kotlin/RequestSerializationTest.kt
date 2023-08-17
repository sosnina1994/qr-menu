import org.junit.Test
import ru.sosninanv.api.v1.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val createRequest = DishCreateRequest(
        requestId = "123",
        debug = DishDebug(
            mode = EDishRequestDebugMode.STUB,
            stub = EDishRequestDebugStubs.BAD_VISIBILITY
        ),
        dish = DishCreateObject(
            name = "dish name",
            description = "dish description",
            cost = 500.5,
            visibility = EDishVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(createRequest)

        assertContains(json, Regex("\"name\":\\s*\"dish name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badVisibility\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(createRequest)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DishCreateRequest

        assertEquals(createRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, DishCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}