/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.config.elastic.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import net.parlacom.common.config.constant.MediaType.DATETIME_FORMAT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT)

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext) : LocalDateTime {
        return LocalDateTime.parse(p.getValueAsString(), FORMATTER);
    }
}