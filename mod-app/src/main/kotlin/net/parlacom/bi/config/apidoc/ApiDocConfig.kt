/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.apidoc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Migrating from SpringFox: https://springdoc.org/#migrating-from-springfox
 *
 * curl --location 'https://api.parlacom.net/v3/api-docs'
 * curl --location 'https://api.parlacom.net/v1/api-docs.html
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
@Configuration
class ApiDocConfig {
    companion object{
        const val SECURITY_SCHEME = "Bearer"
        const val BEARER_FORMAT = "JWT"
        const val AUTHORIZATION = "Authorization"
    }

    /**
     * TODO
     *
     * @return
     */
    @Bean
    fun apiOpenApi(@Value("\${apidoc.group}") group: String,
                   @Value("\${apidoc.packaged-to-match}") packagedToMatch: String) : GroupedOpenApi? {
        val packages = arrayOf(packagedToMatch)

        return GroupedOpenApi.builder()
                             .group(group)
                             .packagesToScan(*packages)
                             .build()
    }

    /**
     * TODO
     *
     * @param title
     * @param description
     * @param licenseName
     * @param licenseUrl
     * @param termsOfService
     * @param contactName
     * @param contactUrl
     * @param contactEmail
     * @param version
     * @return
     */
    @Bean
    fun customOpenAPI(@Value("\${apidoc.title}") title: String,
                      @Value("\${apidoc.description}") description: String,
                      @Value("\${apidoc.license-name}") licenseName: String,
                      @Value("\${apidoc.license-url}") licenseUrl: String,
                      @Value("\${apidoc.terms-of-service}") termsOfService: String,
                      @Value("\${apidoc.contact-name}") contactName: String,
                      @Value("\${apidoc.contact-url}") contactUrl: String,
                      @Value("\${apidoc.contact-email}") contactEmail: String,
                      @Value("\${apidoc.version}") version: String) : OpenAPI {
        return OpenAPI().components(Components().addSecuritySchemes(
            SECURITY_SCHEME, SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name(AUTHORIZATION)
                .`in`(SecurityScheme.In.HEADER)
                .bearerFormat(BEARER_FORMAT)
                .description("Enter the token with the `Bearer: ` prefix, e.g. \"Bearer abcde12345\"")))
            .info(Info().title(title)
                .version(version)
                .description(description)
                .contact(Contact().name(contactName).email(contactEmail).url(contactUrl))
                .termsOfService(termsOfService)
                .license(License().name(licenseName).url(licenseUrl)))
    }
}