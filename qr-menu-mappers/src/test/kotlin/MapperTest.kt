import models.*
import org.junit.Test
import ru.sosninanv.api.v1.models.*
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = DishCreateRequest(
            requestId = "1234",
            debug = DishDebug(
                mode = EDishRequestDebugMode.TEST,
                stub = EDishRequestDebugStubs.SUCCESS,
            ),
            dish = DishCreateObject(
                name = "name",
                description = "desc",
                cost = 500.5,
                dishType = EDishType.MAIN,
                visibility = EDishVisibility.PUBLIC,
            ),
        )

        val context = QrMenuContext()
        context.fromTransport(req)

        assertEquals(EDishRequestDebugStubs.SUCCESS.name, context.stubCase.name)
        assertEquals(EDishRequestDebugMode.TEST.name, context.workMode.name)
        assertEquals("name", context.dishRequest.name)
        assertEquals(EDishVisibility.PUBLIC.name, context.dishRequest.visibility.name)
    }

    @Test
    fun toTransport() {
        val context = QrMenuContext(
            requestId = QrMenuRequestId("1234"),
            command = EQrMenuCommand.CREATE,
            dishResponse = QrMenuDish(
                name = "name",
                description = "desc",
                cost = 500.5,
                type = EQrMenuDishType.MAIN,
                visibility = EQrMenuVisibility.PUBLIC,
            ),
            errors = mutableListOf(
                QrMenuError(
                    code = "err",
                    group = "request",
                    field = "field",
                    message = "wrong title",
                )
            ),
            state = EQrMenuState.RUNNING,
        )

        val req = context.toTransport() as DishCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.dish?.name)
        assertEquals("desc", req.dish?.description)
        assertEquals(EDishType.MAIN, req.dish?.dishType)
        assertEquals(EDishVisibility.PUBLIC, req.dish?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("field", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }

}
