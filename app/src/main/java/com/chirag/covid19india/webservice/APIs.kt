package com.chirag.covid19india.webservice

import com.chirag.covid19india.util.AppConstants

object APIs {
    const val BASE_URL = AppConstants.BASE_DOMAIN

    //National Data
    const val API_NATIONAL_DATA = "/data.json"

    //State-District Data
    const val API_STATE_DISTRICT_DATA = "/v2/state_district_wise.json"

    //Daily Updates
    const val API_DAILY_UPDATES = "updatelog/log.json"
}