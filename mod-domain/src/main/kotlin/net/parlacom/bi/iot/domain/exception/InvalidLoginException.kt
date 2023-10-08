/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.domain.exception

import net.parlacom.bi.iot.domain.exception.error.ApiErrorCode.MC1102
import net.parlacom.common.exception.BusinessException

/**
 * Class description
 *
 * @author Luiz Moraes
 * @version 1.0, 2021-10-28
 */
class InvalidLoginException : BusinessException {
    /**
     *
     */
    constructor() : super(MC1102)

    /**
     *
     */
    constructor(ex: Exception) : super(ex, MC1102)

    /**
     *
     */
    constructor(message: String) : super(message, MC1102)
}
