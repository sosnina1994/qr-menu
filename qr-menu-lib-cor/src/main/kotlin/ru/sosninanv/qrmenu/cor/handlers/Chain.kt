package ru.sosninanv.qrmenu.cor.handlers

import ru.sosninanv.qrmenu.cor.CorDslMarker
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.ICorExec
import ru.sosninanv.qrmenu.cor.base.BaseCorChain
import ru.sosninanv.qrmenu.cor.base.BaseCorChainDsl


@CorDslMarker
fun <T> ICorAddExecDsl<T>.chain(function: CorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : BaseCorChain<T>(
    title = title,
    description = description,
    blockOn = blockOn,
    blockExcept = blockExcept
) {
    override suspend fun handle(context: T) {
        execs.forEach { it.exec(context) }
    }
}


/**
 * DLS is the execution context of multiple chains.
 * It can be expanded by other chains.
 * The chains are executed sequentially.
 */
@CorDslMarker
class CorChainDsl<T>() : BaseCorChainDsl<T, T>() {
    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        execs = workers.map { it.build() }.toList(),
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}
