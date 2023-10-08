/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.controller.payload

import net.parlacom.bi.iot.controller.payload.presenter.PayloadPresenter
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_COMPANY
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_DEVICE_EUI
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_END_DATETIME
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_LOGIN
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_START_DATETIME
import net.parlacom.bi.iot.service.payload.PayloadService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import kotlin.jvm.optionals.getOrNull

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
@RestController
class PayloadControllerHandler(private val presenter: PayloadPresenter,
                               private val service: PayloadService) {
    /**
     * TODO
     *
     * @param request
     * @return
     */
    suspend fun getPayloadsByClient(request: ServerRequest) : ServerResponse {

        // Validate the request parameters and build the domain logic model
        val clientConnection = presenter.getClientConnection(request)

        return presenter.coResponse(service.getPayloadsByClient(clientConnection.client,
                                                                clientConnection.connection))
    }
}
