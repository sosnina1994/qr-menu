package ru.sosninanv.qrmenu.app.rabbit.config

import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.sosninanv.qrmenu.app.rabbit.controller.RabbitController
import ru.sosninanv.qrmenu.app.rabbit.processor.RabbitDirectProcessor
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor

data class AppSettings(
    val config: RabbitConfig = RabbitConfig(),
    val dishProcessor: QrMenuDishProcessor = QrMenuDishProcessor(),
    val producerConfig: RabbitExchangeConfiguration = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = "transport-exchange",
        queue = "v1-queue",
        consumerTag = "v1-consumer",
        exchangeType = "direct"
    ),
    val processor: RabbitDirectProcessor = RabbitDirectProcessor(
        config = config,
        processorConfig = producerConfig,
        processor = dishProcessor
    ),
    val controller: RabbitController = RabbitController(
        processors = setOf(processor)
    )
)
