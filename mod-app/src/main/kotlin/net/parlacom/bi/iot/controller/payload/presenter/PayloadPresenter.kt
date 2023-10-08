/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.controller.payload.presenter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_COMPANY
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_DEVICE_EUI
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_END_DATETIME
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_LOGIN
import net.parlacom.bi.iot.domain.constant.ApiPayloadConstant.PARAM_START_DATETIME
import net.parlacom.bi.iot.domain.exception.InvalidCompanyException
import net.parlacom.bi.iot.domain.exception.InvalidEndDateTimeException
import net.parlacom.bi.iot.domain.exception.InvalidLoginException
import net.parlacom.bi.iot.domain.exception.InvalidStartDateTimeException
import net.parlacom.bi.iot.domain.model.device.Connection
import net.parlacom.common.config.constant.MediaType.DATETIME_FORMAT
import net.parlacom.common.domain.dto.payload.universal.PayloadUniversalDocument
import net.parlacom.common.domain.dto.payload.universal.PayloadUniversalDto
import net.parlacom.common.domain.mapper.payload.DevicePayloadDocumentMapper
import net.parlacom.common.domain.model.client.Client
import net.parlacom.common.domain.model.company.Company
import net.parlacom.common.library.datetime.DateTimeLib.parseDate
import net.parlacom.common.library.string.StringLib.isEmpty
import net.parlacom.common.presenter.Presenter
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import kotlin.jvm.optionals.getOrNull

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
@Component
class PayloadPresenter : Presenter() {

    /**
     * Class that brings semantic to Pair instance
     *
     * @param A -> client
     * @param B -> connection
     */
    data class ClientConnection<out A, out B>(val client: A, val connection: B)

    /**
     * TODO
     *
     * @param request
     * @return
     */
    fun getClientConnection(req: ServerRequest) : ClientConnection<Client, Connection> {
        if (isEmpty(getParam(PARAM_COMPANY, req))) throw InvalidCompanyException()
        if (isEmpty(getParam(PARAM_LOGIN, req))) throw InvalidLoginException()
        if (isEmpty(getParam(PARAM_START_DATETIME, req))) throw InvalidStartDateTimeException()
        if (isEmpty(getParam(PARAM_END_DATETIME, req))) throw InvalidEndDateTimeException()

        val client = Client(company=Company(getParam(PARAM_COMPANY, req)!!),
                            username=getParam(PARAM_LOGIN, req)!!)

        val connection = Connection(deviceEui=getParam(PARAM_DEVICE_EUI, req),
                                    startDateTime=parseDate(getParam(PARAM_START_DATETIME, req)!!, DATETIME_FORMAT),
                                    endDateTime=parseDate(getParam(PARAM_END_DATETIME, req)!!, DATETIME_FORMAT))

        return ClientConnection(client, connection)
    }

    /**
     * TODO
     *
     * @param param
     * @param request
     * @return
     */
    private fun getParam(param: String, request: ServerRequest) : String?{
        return request.queryParam(param).getOrNull()
    }

    /**
     * TODO
     *
     * @param payloadUniversalDocuments
     * @return
     */
    suspend fun coResponse(payloadUniversalDocuments: Flow<PayloadUniversalDocument>) : ServerResponse {
        return this.coResponse(payloadUniversalDocuments.map { document ->
                                                                convertToUniversalPayload(document)
                                                           }.toList())
    }

    /**
     * TODO
     *
     * @param payloadUniversalDocument
     * @return
     */
    private fun convertToUniversalPayload(payloadUniversalDocument: PayloadUniversalDocument) : PayloadUniversalDto {
        val devicePayloadDocumentMapper = Mappers.getMapper(DevicePayloadDocumentMapper::class.java)
        return devicePayloadDocumentMapper.convertToUniversalPayload(payloadUniversalDocument)
    }
}

