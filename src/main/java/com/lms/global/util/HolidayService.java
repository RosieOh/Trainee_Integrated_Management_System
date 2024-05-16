package com.lms.global.util;

import com.lms.global.util.Holiday;
import com.lms.global.util.HolidayResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HolidayService {

    private final String API_KEY = "c7TLr0dTRRPJls8s%2FUwinIzNnhHLVVUj5ybeR7dUgg8UVWP44kGHtYAQUpiXudniw3DjDKkvS%2F5VlxSpc5IExw%3D%3D";

    public List<Holiday> getHolidays() {
        String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService";

        RestTemplate restTemplate = new RestTemplate();
        HolidayResponse response = restTemplate.getForObject(apiUrl, HolidayResponse.class);


        return response.getHolidays();
    }

}
