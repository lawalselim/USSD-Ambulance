package com.example.backend.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {
    @Value("${google.maps.api-key}")
    private String apiKey;

    public double[] getCoordinates(String address) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        context.shutdown();

        double lat = results[0].geometry.location.lat;
        double lng = results[0].geometry.location.lng;

        return new double[]{lat, lng};
    }
}
