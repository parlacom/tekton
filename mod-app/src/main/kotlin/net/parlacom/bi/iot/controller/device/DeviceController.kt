///*
// * This file is property of the Parlacom Telecommunications Brazil
// * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
// * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
// * and will be prosecuted to the maximum extent possible under the law.
// */
//package net.parlacom.bi.iot.controller.device
//
//import net.parlacom.common.config.constant.MediaType.ContentType.APPLICATION_JSON
//import net.parlacom.common.presenter.WebResponse
//import net.parlacom.bi.iot.controller.device.presenter.BIPresenter
//import net.parlacom.bi.iot.domain.constant.ApiConstant.API_BASE_URL
//import net.parlacom.bi.iot.domain.constant.ApiConstant.API_V2
//import net.parlacom.bi.iot.domain.constant.ApiConstant.HEADER_AUTHORIZATION
//import net.parlacom.bi.iot.domain.constant.ApiConstant.MESSAGES_MAX_RESULTS
//import net.parlacom.bi.iot.domain.constant.ApiConstant.HEADER_CLIENT_USERNAME
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.PARAM_DEVICE_EUI
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.PARAM_END_DATETIME
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.PARAM_MESSAGES_MAX_RESULTS
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.PARAM_INTERVAL
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.PARAM_START_DATETIME
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_DOWNLINK_MESSAGES
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_INFO
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_LAST_MESSAGES
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_OFFLINE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_ONLINE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_ACTIVE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_DOWNLINK
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_INACTIVE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_MESSAGE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_OFFLINE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_ONLINE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_QUARANTINE
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_TOTAL_UPLINK
//import net.parlacom.bi.iot.domain.constant.ApiDeviceConstant.URI_DEVICES_UPLINK_MESSAGES
//import net.parlacom.bi.iot.service.device.DeviceService
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestHeader
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod.GET
//import org.springframework.web.bind.annotation.RequestMethod.POST
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
///**
// * Class description
// *
// * @author Luiz Moraes
// * @version 1.0, 2021-10-28
// */
//@RestController
//@RequestMapping(API_V2 + API_BASE_URL)
//class DeviceController(val presenter: BIPresenter, val service: DeviceService){
//
//    /**
//     *
//     * @param authorization
//     * @param username
//     * @param deviceEui
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_INFO], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getDeviceByEui(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                               @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                               @PathVariable(value=PARAM_DEVICE_EUI, required=true) deviceEui: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui)
//        return presenter.response(service.getDeviceByEui(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                      @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_ONLINE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalOnlineClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                            @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalOnlineClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_OFFLINE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalOfflineClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                             @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String) : ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalOfflineClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_ACTIVE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalActiveClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                            @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String) : ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalActiveClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_INACTIVE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalInactiveClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                              @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String) : ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalInactiveClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_QUARANTINE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalQuarantineClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                                @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String) : ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username)
//        return presenter.response(service.getTotalQuarantineClientDevices(clientConnection.client))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_DOWNLINK], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalClientDevicesDownlinkMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                                      @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                                      @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?,
//                                                      @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                                      @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.response(service.getTotalClientDevicesDownlinkMessages(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_UPLINK], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalClientDevicesUplinkMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                                    @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                                    @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?,
//                                                    @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                                    @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.response(service.getTotalClientDevicesUplinkMessages(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param deviceEui
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_TOTAL_MESSAGE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getTotalClientDevicesMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                              @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                              @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?,
//                                              @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                              @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.response(service.getTotalClientDevicesMessages(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param interval
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_ONLINE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getOnlineClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                       @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                       @RequestParam(value=PARAM_INTERVAL, required=false) interval: String?,
//                                       @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                       @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, interval=interval, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.responseSeries(service.getOnlineClientDevices(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param interval
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_OFFLINE], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getOfflineClientDevices(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                        @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                        @RequestParam(value=PARAM_INTERVAL, required=false) interval: String?,
//                                        @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                        @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, interval=interval, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.responseSeries(service.getOfflineClientDevices(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param interval
//     * @param deviceEui
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_DOWNLINK_MESSAGES], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getClientDevicesDownlinkMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                                 @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                                 @RequestParam(value=PARAM_INTERVAL, required=false) interval: String?,
//                                                 @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?,
//                                                 @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                                 @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, interval=interval, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.responseSeries(service.getClientDevicesDownlinkMessages(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @param interval
//     * @param deviceEui
//     * @param startDateTime
//     * @param endDateTime
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_UPLINK_MESSAGES], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getClientDevicesUplinkMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                               @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                               @RequestParam(value=PARAM_INTERVAL, required=false) interval: String?,
//                                               @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?,
//                                               @RequestParam(value=PARAM_START_DATETIME, required=true) startDateTime: String,
//                                               @RequestParam(value=PARAM_END_DATETIME, required=true) endDateTime: String): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, interval=interval, startDateTime=startDateTime, endDateTime=endDateTime)
//        return presenter.responseSeries(service.getClientDevicesUplinkMessages(clientConnection.client, clientConnection.connection))
//    }
//
//    /**
//     * TODO
//     *
//     * @param authorization
//     * @param username
//     * @return
//     */
//    @RequestMapping(value=[URI_DEVICES_LAST_MESSAGES], method=[GET, POST], produces=[APPLICATION_JSON])
//    suspend fun getClientDevicesLastMessages(@RequestHeader(value=HEADER_AUTHORIZATION, required=true) authorization: String,
//                                             @RequestHeader(value=HEADER_CLIENT_USERNAME, required=true) username: String,
//                                             @RequestParam(value=PARAM_MESSAGES_MAX_RESULTS, required=false, defaultValue=MESSAGES_MAX_RESULTS.toString()) messagesMaxResults: Int,
//                                             @RequestParam(value=PARAM_DEVICE_EUI, required=false) deviceEui: String?): ResponseEntity<WebResponse> {
//
//        val clientConnection = presenter.getClientConnection(username=username, deviceEui=deviceEui, messagesMaxResults=messagesMaxResults)
//        return presenter.response(service.getClientDevicesLastMessages(clientConnection.client, clientConnection.connection))
//    }
//}
