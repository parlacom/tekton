/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.jooq

import io.r2dbc.pool.PoolingConnectionFactoryProvider.INITIAL_SIZE
import io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_IDLE_TIME
import io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_LIFE_TIME
import io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE
import io.r2dbc.pool.PoolingConnectionFactoryProvider.POOL_NAME
import io.r2dbc.pool.PoolingConnectionFactoryProvider.VALIDATION_QUERY
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Duration

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
@Configuration
class DeviceJooqConfig {
    companion object {
        const val VALIDATION_SQL = "SELECT 1"
        const val POOL_DRIVER = "pool"
    }

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Value("\${datasource.device.driver}")
    lateinit var driver: String

    @Value("\${datasource.device.host}")
    lateinit var host: String

    @Value("\${datasource.device.port}")
    lateinit var port: String

    @Value("\${datasource.device.database}")
    lateinit var database: String

    @Value("\${datasource.device.username}")
    lateinit var username: String

    @Value("\${datasource.device.password}")
    lateinit var password: String

    @Value("\${datasource.device.pool.initial-size}")
    lateinit var initialSize: String

    @Value("\${datasource.device.pool.max-size}")
    lateinit var maxSize: String

    @Value("\${datasource.device.pool.max-life-time-seconds}")
    lateinit var maxLifeTime: String

    /**
     * TODO
     *
     * @return
     */
    @Primary
    @Bean("DeviceConnectionFactory")
    fun connectionFactory() : DSLContext {
        val connectionFactory: ConnectionFactory = ConnectionFactories.get(
            ConnectionFactoryOptions
                .builder()
                    // Datasource Configuration
                    .option(DRIVER, POOL_DRIVER)
                    .option(PROTOCOL, driver) // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                    .option(HOST, host)
                    .option(PORT, port.toInt())
                    .option(DATABASE, database)
                    .option(USER, username)
                    .option(PASSWORD, password)

                    // Connection Pool Configuration
                    .option(POOL_NAME, appName)
                    .option(VALIDATION_QUERY, VALIDATION_SQL)
                    .option(MAX_IDLE_TIME, Duration.ofSeconds(maxLifeTime.toLong()))
                    .option(MAX_LIFE_TIME, Duration.ofSeconds(maxLifeTime.toLong()))
                    .option(INITIAL_SIZE, initialSize.toInt())
                    .option(MAX_SIZE, maxSize.toInt())
                .build()
        )
        return DSL.using(connectionFactory)
    }
}