///*
// * This file is property of the Parlacom Telecommunications Brazil
// * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
// * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
// * and will be prosecuted to the maximum extent possible under the law.
// */
//package net.parlacom.bi.iot.controller.device.presenter;
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.toList
//import net.parlacom.common.config.constant.MediaType.DATETIME_FORMAT
//import net.parlacom.common.config.constant.MediaType.EMPTY
//import net.parlacom.common.config.constant.MediaType.NULL_JSON
//import net.parlacom.common.library.datetime.DateTimeLib.parseDate
//import net.parlacom.common.library.string.StringLib.isEmpty
//import net.parlacom.common.presenter.WebResponse
//import net.parlacom.bi.iot.domain.constant.ApiConstant.DATE_HISTOGRAM_INTERVAL
//import net.parlacom.bi.iot.domain.constant.ApiConstant.MESSAGES_MAX_RESULTS
//import net.parlacom.bi.iot.domain.dto.device.DeviceResDTO
//import net.parlacom.bi.iot.domain.dto.device.DeviceSeriesTotalResDTO
//import net.parlacom.bi.iot.domain.dto.device.DeviceTotalResDTO
//import net.parlacom.iot.bi.domain.model.client.Client
//import net.parlacom.iot.bi.domain.model.company.Company
//import net.parlacom.bi.iot.domain.model.device.Connection
//import net.parlacom.bi.iot.domain.model.device.Device
//import net.parlacom.bi.iot.domain.model.device.DeviceSeriesTotal
//import net.parlacom.security.auth.model.User
//import net.parlacom.security.auth.presenter.SecurityPresenter
//import org.springframework.http.ResponseEntity
//import org.springframework.stereotype.Component
//
///**
// * Class description
// *
// * @author Luiz Moraes
// * @version 1.0, 2021-10-28
// */
//@Component
//class DevicePresenter(val presenter: SecurityPresenter) {
//
//    /**
//     * Class that brings semantic to Pair instance
//     *
//     * @param A -> client
//     * @param B -> connection
//     */
//    data class Pair<out A, out B>(val client: A, val connection: B)
//
//    /**
//     * TODO
//     *
//     * @param username
//     * @param deviceEui
//     * @param interval
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    fun getClientConnection(username: String, messagesMaxResults: Int=MESSAGES_MAX_RESULTS, interval: String?=EMPTY, deviceEui: String?=EMPTY, startDateTime: String=EMPTY, endDateTime: String=EMPTY) : Pair<Client, Connection> {
//        val authUser: User = presenter.getAuthUser()
//        val clientUsername = presenter.getClientUsername(username)
//        val dateHistogramInterval = if (isEmpty(interval)) DATE_HISTOGRAM_INTERVAL else interval
//
//        val client = Client(Company(authUser.company), clientUsername)
//
//        val connection = if (!isEmpty(startDateTime)){
//             Connection(interval=dateHistogramInterval,
//                        deviceEui=deviceEui,
//                        messagesMaxResults=messagesMaxResults,
//                        startDateTime=parseDate(startDateTime, DATETIME_FORMAT),
//                        endDateTime=parseDate(endDateTime, DATETIME_FORMAT))
//        }else{
//            Connection(interval=dateHistogramInterval,
//                       deviceEui=deviceEui,
//                       messagesMaxResults=messagesMaxResults)
//        }
//        return Pair(client, connection)
//    }
//
//    /**
//     *
//     * @param deviceTotal
//     * @return
//     */
//    suspend fun response(deviceTotal: Long): ResponseEntity<WebResponse> {
//      return this.presenter.response(DeviceTotalResDTO(total=deviceTotal))
//    }
//
//    /**
//     * TODO
//     *
//     * @param devicesSeries
//     * @return
//     */
//    suspend fun responseSeries(devicesSeries: Flow<DeviceSeriesTotal?>): ResponseEntity<WebResponse> {
//        val devicesSeries: List<DeviceSeriesTotalResDTO?> = devicesSeries.map { deviceSeries ->
//            deviceSeries?.let { series ->
//                DeviceSeriesTotalResDTO(totalDevices=series.totalDevices,
//                                        totalMessages=series.totalMessages,
//                                        dateTime=series.dateTime)
//            }
//        }.toList()
//        return this.presenter.response(devicesSeries)
//    }
//
//    /**
//     *
//     * @param devices
//     * @return
//     */
//    suspend fun response(devices: Flow<Device?>): ResponseEntity<WebResponse> {
//        val devices: List<DeviceResDTO?> = devices.map { device ->
//                                            device?.let { device ->
//                                                DeviceResDTO(company=device.company,
//                                                             username=device.username,
//                                                             deviceEui=device.deviceEui,
//                                                             connector=device.connector,
//                                                             eventType=device.eventType,
//                                                             status=device.status,
//                                                             activationDate=device.activationDate,
//                                                             registerDate=device.registerDate,
//                                                             connectionTime=device.connectionTime,
//                                                             payload=device.payload,
//                                                             data=device.data)
//                                                }
//        }.toList()
//      return this.presenter.response(devices)
//    }
//
//    /**
//     * TODO
//     *
//     * @param device
//     * @return
//     */
//    suspend fun response(device: Device?): ResponseEntity<WebResponse> {
//
//        return if (device!=null){
//            this.presenter.response(
//                DeviceResDTO(company=device.company,
//                                                 username=device.username,
//                                                 deviceEui=device.deviceEui,
//                                                 connector=device.connector,
//                                                 eventType=device.eventType,
//                                                 status=device.status,
//                                                 activationDate=device.activationDate,
//                                                 registerDate=device.registerDate,
//                                                 connectionTime=device.connectionTime,
//                                                 payload=device.payload,
//                                                 data=device.data)
//            )
//        }else{
//            this.presenter.response(NULL_JSON)
//        }
//    }
//}
//
