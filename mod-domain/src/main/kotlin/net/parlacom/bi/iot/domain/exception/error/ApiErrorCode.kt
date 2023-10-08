/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.domain.exception.error

import net.parlacom.bi.iot.domain.exception.error.ApiErrorKey.*
import net.parlacom.common.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * Messages Code:
 *
 * MC0001 - MC9999 => Reserved to general messages. View
 * net/parlacom/core/i18n/ApiMessageCode.java MC1001 - ∞ => Reserved to app messages
 */
enum class ApiErrorCode(private val httpStatus: HttpStatus, private val apiErrorKey: String) : ErrorCode {

    MC1101(HttpStatus.UNPROCESSABLE_ENTITY, INVALID_COMPANY_ERROR.key),
    MC1102(HttpStatus.UNPROCESSABLE_ENTITY, INVALID_LOGIN_ERROR.key),
    MC1103(HttpStatus.UNPROCESSABLE_ENTITY, INVALID_START_DATETIME_ERROR.key),
    MC1104(HttpStatus.UNPROCESSABLE_ENTITY, INVALID_END_DATETIME_ERROR.key);

    override fun getErrorKey(): String {
        return this.apiErrorKey
    }

    override fun getHttpStatus(): HttpStatus {
        return this.httpStatus
    }
}
