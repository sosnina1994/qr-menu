package ru.sosninanv.qrmenu.app.rabbit.processor

import QrMenuContext
import apiV1Mapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import fromTransport
import helpers.addError
import helpers.asQrMenuError
import kotlinx.datetime.Clock
import models.EQrMenuState
import ru.sosninanv.qrmenu.app.rabbit.config.RabbitConfig
import ru.sosninanv.qrmenu.app.rabbit.config.RabbitExchangeConfiguration
import ru.sosninanv.api.v1.models.IRequest
import ru.sosninanv.qrmenu.app.rabbit.RabbitProcessorBase
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import toTransport

class RabbitDirectProcessor(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: QrMenuDishProcessor = QrMenuDishProcessor(),
) : RabbitProcessorBase(config, processorConfig) {

    override suspend fun Channel.processMessage(message: Delivery) {
        val context = QrMenuContext()
        context.apply {
            timeStart = Clock.System.now()
        }

        apiV1Mapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = processor.exec(context).run { context.toTransport() }
        apiV1Mapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable) {
        val context = QrMenuContext()
        e.printStackTrace()
        context.state = EQrMenuState.FAILING
        context.addError(error = arrayOf(e.asQrMenuError()))
        val response = context.toTransport()
        apiV1Mapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}
