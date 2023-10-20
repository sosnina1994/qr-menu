import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import models.EQrMenuDishType
import models.EQrMenuPermissionClient
import models.EQrMenuVisibility
import models.QrMenuUserId
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.RabbitMQContainer
import ru.sosninanv.qrmenu.app.rabbit.config.RabbitConfig
import ru.sosninanv.qrmenu.app.rabbit.config.RabbitExchangeConfiguration
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.qrmenu.app.rabbit.config.AppSettings
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RabbitMqTest {

    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"

        private val container = run {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
            RabbitMQContainer("rabbitmq:latest").apply {
                withExposedPorts(5672, 15672)
                withUser("guest", "guest")
            }
        }

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            container.start()
        }
        @AfterClass
        @JvmStatic
        fun afterAll() {
            container.stop()
        }
    }

    private val appSettings by lazy {
        AppSettings(
            config = RabbitConfig(
                port = container.getMappedPort(5672)
            ),
            producerConfig = RabbitExchangeConfiguration(
                keyIn = "in-v1",
                keyOut = "out-v1",
                exchange = exchange,
                queue = "v1-queue",
                consumerTag = "test-tag",
                exchangeType = exchangeType
            )
        )
    }

    @BeforeTest
    fun tearUp() {
        appSettings.controller.start()
    }

    @AfterTest
    fun tearDown() {
        appSettings.controller.close()
    }

    @Test
    fun dishCreate() {
        val (keyOut, keyIn) = with(appSettings.processor.processorConfig) { Pair(keyOut, keyIn) }
        val (tstHost, tstPort) = with(appSettings.config) { Pair(host, port) }
        ConnectionFactory().apply {
            host = tstHost
            port = tstPort
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiV1Mapper.writeValueAsBytes(dishCreate))

                runBlocking {
                    withTimeoutOrNull(265L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiV1Mapper.readValue(responseJson, DishCreateResponse::class.java)
                val expected = QrMenuDishStub.get()

                assertEquals(expected.name, response.dish?.name)
                assertEquals(expected.description, response.dish?.description)
            }
        }
    }



    private val dishCreate = with(QrMenuDishStub.get()) {
        DishCreateRequest(
            requestType = "create",
            requestId = "1234",
            debug = DishDebug(
                mode = EDishRequestDebugMode.STUB,
                stub = EDishRequestDebugStubs.SUCCESS,
            ),
            dish = DishCreateObject(
                name = "Dish1",
                description = "Author",
                cost = 100.0,
                dishType = EDishType.DESSERT,
                visibility = EDishVisibility.PUBLIC
            )
        )
    }

}
