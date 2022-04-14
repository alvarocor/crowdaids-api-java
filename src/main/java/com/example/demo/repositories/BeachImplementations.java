package com.example.demo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
@Transactional
public class BeachImplementations implements BeachRepository {

    @Override
    public List retrieveSwellConditions(String id) {
        String url = "http://services.surfline.com/kbyg/spots/forecasts/wave?spotId=" + id + "&days=6&intervalHours=1";
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap result = ((LinkedHashMap) restTemplate.getForObject(url, Object.class));
        //String result = restTemplate.getForObject(url, String.class);

        List swell = new ArrayList();
        swell.add(result);

        return swell;
    }
}
