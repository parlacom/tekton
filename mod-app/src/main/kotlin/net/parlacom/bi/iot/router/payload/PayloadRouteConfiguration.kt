/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.router.payload

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import kotlinx.coroutines.FlowPreview
import net.parlacom.bi.iot.controller.payload.PayloadControllerHandler
import net.parlacom.bi.iot.domain.constant.ApiConstant.API_BASE_URL
import net.parlacom.bi.iot.domain.constant.ApiConstant.API_V1
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.URI_PAYLOADS
import net.parlacom.common.config.constant.MediaType.ContentType.APPLICATION_JSON
import net.parlacom.common.domain.dto.payload.universal.PayloadUniversalDto
import net.parlacom.common.presenter.WebResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.reactive.function.server.coRouter

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
@Configuration
class PayloadRouteConfiguration {
    @Bean
    @FlowPreview
    @RouterOperations(value=[RouterOperation(path="$API_V1$API_BASE_URL$URI_PAYLOADS",
                                             method=[GET],
                                             produces=[APPLICATION_JSON],
                                             operation=Operation(operationId="getPayloadsByClient",
                                                                 tags=["Payloads"],
                                                                 summary="Fetch device payloads by Client",
                                                                 security=[SecurityRequirement(name="Bearer")],
                                                                 parameters=[Parameter(name="company", required=true, `in`=QUERY),
                                                                             Parameter(name="login", required=true, `in`=QUERY),
                                                                             Parameter(name="startDateTime", required=true, `in`=QUERY, description="Example: 2023-01-01T01:00:00 (yyyy-MM-ddTHH:mm:ss)"),
                                                                             Parameter(name="endDateTime", required=true, `in`=QUERY, description="Example: 2023-01-10T23:59:59 (yyyy-MM-ddTHH:mm:ss)"),
                                                                             Parameter(name="deviceEui", required=false, `in`=QUERY)],
                                                                 responses=[ApiResponse(responseCode="200",
                                                                                        description="Successful request",
                                                                                        content=[Content(mediaType=APPLICATION_JSON, array=ArraySchema(schema=Schema(implementation=PayloadUniversalDto::class)))]),
                                                                            ApiResponse(responseCode="422",
                                                                                        description="Request was not acceptable due a business logic rule",
                                                                                        content=[Content(mediaType=APPLICATION_JSON, schema=Schema(implementation=WebResponse::class))]),
                                                                            ApiResponse(responseCode="500",
                                                                                        description="Internal server error",
                                                                                        content=[Content(mediaType=APPLICATION_JSON, schema=Schema(implementation=WebResponse::class))])]))])
    fun routes(payloadControllerHandler: PayloadControllerHandler) =
        coRouter { "$API_V1$API_BASE_URL".nest {
            GET(URI_PAYLOADS, payloadControllerHandler::getPayloadsByClient)
        }
    }
}
