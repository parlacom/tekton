/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.elastic

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.client.erhlc.ReactiveRestClients
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import javax.net.ssl.SSLContext

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
@Configuration
class ElasticClientConfig(private val sslContext: SSLContext) {

    @Value("\${elastic.endpoints}")
    lateinit var endpoints: String

    @Value("\${elastic.username}")
    lateinit var username: String

    @Value("\${elastic.password}")
    lateinit var password: String

    /**
     * TODO
     *
     * @return
     */
    @Bean
    fun reactiveElasticsearchClient() : ReactiveElasticsearchClient {
        val clientConfiguration = ClientConfiguration.builder()
            .connectedTo(endpoints)
            .usingSsl(sslContext)
            .withBasicAuth(username, password)
            .withSocketTimeout(0)
            .withConnectTimeout(0)
            .withClientConfigurer(
                ReactiveRestClients.WebClientConfigurationCallback.from { webClient ->
                    val exchangeStrategies = ExchangeStrategies.builder()
                        .codecs { configurer: ClientCodecConfigurer ->
                            configurer.defaultCodecs()
                                .maxInMemorySize(-1)
                        }
                        .build()
                    webClient.mutate().exchangeStrategies(exchangeStrategies).build()
                }
            ).build()
        return ElasticsearchClients.createReactive(clientConfiguration)
    }

    /**
     * TODO
     *
     * @return
     */
    @Bean
    fun reactiveElasticsearchTemplate() : ReactiveElasticsearchTemplate {
        return ReactiveElasticsearchTemplate(reactiveElasticsearchClient(), MappingElasticsearchConverter(SimpleElasticsearchMappingContext()))
    }
}