package ru.sosninanv.qrmenu.cor

import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.sosninanv.qrmenu.cor.handlers.chain
import ru.sosninanv.qrmenu.cor.handlers.worker
import ru.sosninanv.qrmenu.cor.helper.CorStatuses
import ru.sosninanv.qrmenu.cor.helper.TestContext
import kotlin.test.assertEquals

class CorBaseTest {
    @Test
    fun createCor() = runTest {
        val ctx = TestContext(some = 0)
        chain.exec(ctx)
        assertEquals(CorStatuses.RUNNING, ctx.status)
        assertEquals(1, ctx.some)
        assertEquals("", ctx.text)
    }

    companion object {
        val chain = rootChain<TestContext> {
            worker {
                title = "Initialization"
                description = "Check the status initialization"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.FAILING }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                worker(
                    title = "First worker",
                    description = "First worker"
                ) {
                    some += 1
                }
            }

            printResult()

        }.build()

    }
}

private fun ICorAddExecDsl<TestContext>.printResult() = worker(title = "Print example") {
    println("some = $some")
}