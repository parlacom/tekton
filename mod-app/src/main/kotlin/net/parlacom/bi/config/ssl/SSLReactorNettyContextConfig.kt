/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.ssl

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
@Configuration
class SSLReactorNettyContextConfig {

    /**
     * Build the SSL Context and by pass the certificate check
     *
     * @return
     */
    @Bean
    fun setupSslReactorNettyContextConfig() : SslContext {
        return SslContextBuilder.forClient()
                                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                .build()
    }
}