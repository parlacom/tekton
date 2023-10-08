/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.service.payload

import kotlinx.coroutines.flow.Flow
import net.parlacom.bi.iot.domain.model.device.Connection
import net.parlacom.common.domain.dto.payload.universal.PayloadUniversalDocument
import net.parlacom.common.domain.model.client.Client

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-01-07
 */
interface PayloadService {

    /**
     * TODO
     *
     * @param client
     * @param connection
     * @return
     */
    suspend fun getPayloadsByClient(client: Client, connection: Connection) : Flow<PayloadUniversalDocument>
}