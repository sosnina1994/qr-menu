package ru.sosninanv.qrmenu.biz

import DishRepoStub
import QrMenuCorSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.EQrMenuCommand
import ru.sosninanv.qrmenu.biz.validation.*
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = EQrMenuCommand.UPDATE
    private val settings by lazy { QrMenuCorSettings(repoTest = DishRepoStub()) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @Test fun correctName() = validationNameCorrect(command, processor)
    @Test fun trimTitle() = validationNameTrim(command, processor)
    @Test fun emptyTitle() = validationNameEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationNameSymbols(command, processor)

    @Test fun validationLockCorrect() = validationLockCorrect(command, processor)
    @Test fun validationLockTrim() = validationLockTrim(command, processor)
    @Test fun validationLockEmpty() = validationLockEmpty(command, processor)
    @Test fun validationLockFormat() = validationLockFormat(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

