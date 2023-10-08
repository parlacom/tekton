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
import net.parlacom.common.config.constant.MediaType.DATE_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-02-27
 */
class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
    val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext) : LocalDate {
        return LocalDate.parse(p.getValueAsString(), FORMATTER);
    }
}