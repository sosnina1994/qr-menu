package ru.sosninanv.qrmenu.cor.base

import ru.sosninanv.qrmenu.cor.*

abstract class BaseCorWorkerDsl<T>(
    override var title: String = "",
    override var description: String = "",
    var blockOn: suspend T.() -> Boolean = { true },
    var blockHandle: suspend T.() -> Unit = {},
    var blockExcept: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e },
) : ICorExecDsl<T>, ICorOnDsl<T>, ICorExceptDsl<T>, ICorHandleDsl<T> {

    abstract override fun build(): ICorExec<T>

    override fun on(function: suspend T.() -> Boolean) {
        blockOn = function
    }

    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun except(function: suspend T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

}
