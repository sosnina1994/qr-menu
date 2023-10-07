package ru.sosninanv.qrmenu.cor

import kotlinx.coroutines.test.runTest
import ru.sosninanv.qrmenu.cor.helper.TestContext
import kotlin.test.Test
import kotlin.test.assertEquals

class CorTest {

    @Test
    fun corTest() = runTest {
        val chain = CorBaseTest.chain
        val ctx = TestContext(some = 1)

        chain.exec(ctx)

        assertEquals(2, ctx.some)
    }

}
