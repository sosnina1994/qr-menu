package ru.sosninanv.qrmenu.biz

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
    private val processor by lazy { QrMenuDishProcessor() }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

