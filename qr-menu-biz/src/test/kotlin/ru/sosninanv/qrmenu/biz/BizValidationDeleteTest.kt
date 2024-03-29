package ru.sosninanv.qrmenu.biz

import DishRepoStub
import QrMenuCorSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.EQrMenuCommand
import ru.sosninanv.qrmenu.biz.validation.validationIdCorrect
import ru.sosninanv.qrmenu.biz.validation.validationIdEmpty
import ru.sosninanv.qrmenu.biz.validation.validationIdFormat
import ru.sosninanv.qrmenu.biz.validation.validationIdTrim
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = EQrMenuCommand.DELETE
    private val settings by lazy { QrMenuCorSettings(repoTest = DishRepoStub()) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
    @Test fun validationLockCorrect() = ru.sosninanv.qrmenu.biz.validation.validationLockCorrect(command, processor)
    @Test fun validationLockTrim() = ru.sosninanv.qrmenu.biz.validation.validationLockTrim(command, processor)
    @Test fun validationLockEmpty() = ru.sosninanv.qrmenu.biz.validation.validationLockEmpty(command, processor)
    @Test fun validationLockFormat() = ru.sosninanv.qrmenu.biz.validation.validationLockFormat(command, processor)


}

