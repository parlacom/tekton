/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.ssl

import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.ssl.SSLContexts
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.net.ssl.SSLContext

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
@Configuration
class SSLContextConfig {

    /**
     * Build the SSL Context and by pass the certificate check
     *
     * @return
     */
    @Bean
    fun setupSSLContext(): SSLContext {
        val sslBuilder: SSLContextBuilder = SSLContexts.custom()
            .loadTrustMaterial(null) { x509Certificates, s -> true }
        return sslBuilder.build()
    }
}