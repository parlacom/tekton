/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.domain.model.device

import net.parlacom.common.config.constant.MediaType.EMPTY
import java.time.LocalDateTime

/**
 * Device domain model
 *
 * @author: Luiz Moraes
 * @version: 1.0, 10-11-2021
 */
data class Connection(val interval: String?=null,
                      val deviceEui: String?=EMPTY,
                      val maxResults: Int=100,
                      val startDateTime: LocalDateTime?=null,
                      val endDateTime: LocalDateTime?=null)