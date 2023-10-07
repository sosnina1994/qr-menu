package ru.sosninanv.qrmenu.cor

import ru.sosninanv.qrmenu.cor.handlers.CorChainDsl
import ru.sosninanv.qrmenu.cor.handlers.CorWorkerDsl

interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun build(): ICorExec<T>
}

interface ICorOnDsl<T> {
    fun on(function: suspend T.() -> Boolean)
}

interface ICorExceptDsl<T> {
    fun except(function: suspend T.(e: Throwable) -> Unit)
}

interface ICorAddExecDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

interface ICorHandleDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

interface ICorExec<T> {
    suspend fun exec(context: T)
}

interface ICorWorker<T> : ICorExec<T> {
    val title: String
    val description: String
    suspend fun on(context: T): Boolean
    suspend fun except(context: T, e: Throwable)
    suspend fun handle(context: T)

    override suspend fun exec(context: T) {
        if (on(context)) {
            try {
                handle(context)
            } catch (e: Throwable) {
                except(context, e)
            }
        }
    }
}

fun <T> rootChain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)
fun <T> rootWorker(function: CorWorkerDsl<T>.() -> Unit) = CorWorkerDsl<T>().apply(function)