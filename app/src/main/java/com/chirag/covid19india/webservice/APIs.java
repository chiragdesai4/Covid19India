package com.chirag.covid19india.webservice;

import static com.chirag.covid19india.util.AppConstants.BASE_DOMAIN;

public class APIs {


    static final String BASE_URL = BASE_DOMAIN;
    public static final String BASE_IMAGE_PATH = BASE_DOMAIN;

    public static final String LOAD_MORE_LIMIT = "10";

    public static final String DEMO_API = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";


    //National Data
    public static final String API_NATIONAL_DATA = "/data.json";

    //State-District Data
    public static final String API_STATE_DISTRICT_DATA = "/v2/state_district_wise.json";

    //Daily Updates
    public static final String API_DAILY_UPDATES = "updatelog/log.json";

}