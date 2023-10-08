/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.repository.payload.elastic

import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.json.JsonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import net.parlacom.bi.iot.domain.constant.RepositoryPayloadConstant.FIELD_COMPANY
import net.parlacom.bi.iot.domain.constant.RepositoryPayloadConstant.FIELD_DEVICE_EUI
import net.parlacom.bi.iot.domain.constant.RepositoryPayloadConstant.FIELD_EPOCH
import net.parlacom.bi.iot.domain.constant.RepositoryPayloadConstant.FIELD_LOGIN
import net.parlacom.bi.iot.domain.model.device.Connection
import net.parlacom.common.domain.dto.payload.universal.PayloadUniversalDocument
import net.parlacom.common.domain.model.client.Client
import net.parlacom.common.library.datetime.DateTimeLib.parseDateTimeToEpochSecond
import net.parlacom.common.library.string.StringLib.isEmpty
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
@Repository
class ElasticPayloadRepository(private val template: ReactiveElasticsearchTemplate) : PayloadRepository {

    /**
     * TODO
     *
     * @param client
     * @param connection
     * @return
     */
    override suspend fun findPayloadsByClient(client: Client, connection: Connection) : Flow<PayloadUniversalDocument> {
        val query: org.springframework.data.elasticsearch.core.query.Query = NativeQuery.builder()
            .withQuery { it.bool { it ->
                it.must {
                    it.term { t-> t.field(FIELD_COMPANY).value(client.company.name) }
                }.must {
                    it.term { t-> t.field(FIELD_LOGIN).value(client.username) }
                }.must {
                    if (!isEmpty(connection.deviceEui)){
                        it.term { t-> t.field(FIELD_DEVICE_EUI).value(connection.deviceEui) }
                    }else{
                        it.bool{ it }
                    }
                }.must {
                    it.range { t-> t.field(FIELD_EPOCH).gte(JsonData.of(parseDateTimeToEpochSecond(connection.startDateTime!!)))
                                   t.field(FIELD_EPOCH).lte(JsonData.of(parseDateTimeToEpochSecond(connection.endDateTime!!)))}
                    }
                }
            }
            .withSort { it.field { it.field(FIELD_EPOCH).order(SortOrder.Desc) } }
            .withMaxResults(connection.maxResults)
            .build()

        val searchResult: Flux<SearchHit<PayloadUniversalDocument>> = template.search(query, PayloadUniversalDocument::class.java)

        return searchResult.map {
            return@map it.content
        }.asFlow()
    }
}

