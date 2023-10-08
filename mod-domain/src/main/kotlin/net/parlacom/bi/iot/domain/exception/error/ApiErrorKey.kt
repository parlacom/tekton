/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.bi.iot.domain.exception.error

/**
 * Collection of all the keys used for localization.
 *
 * @author Luiz Moraes
 * @version 1.0, 2018-11-12
 */
enum class ApiErrorKey(val key: String) {
    INVALID_COMPANY_ERROR("invalid.company.error"),
    INVALID_LOGIN_ERROR("invalid.login.error"),
    INVALID_START_DATETIME_ERROR("invalid.start_datetime.error"),
    INVALID_END_DATETIME_ERROR("invalid.end_datetime.error");
}
