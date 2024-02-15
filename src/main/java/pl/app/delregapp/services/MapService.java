package pl.app.delregapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.app.delregapp.models.DTO.LocalizationDTO;
import pl.app.delregapp.models.DTO.Map.GencodeResult;
import pl.app.delregapp.models.DTO.Matrix.DistanceMatrixResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class MapService {

    @Value("${map.service.geocode.key}")
    private String geocodeKey;

    @Value("${map.service.geocode.url}")
    private String geocodeUrl;

    @Value("${map.service.distancematrix.key}")
    private String distanceMatrixKey;

    @Value("${map.service.distancematrix.url}")
    private String distanceMatrixUrl;
    ObjectMapper mapper = new ObjectMapper();


    public GencodeResult getAddressPosition(LocalizationDTO localization) throws IOException {
        String address = "address=" + getAddressFromLocalization(localization);
        String key = "&key=" + geocodeKey;
        String urlString = geocodeUrl + address + key;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "application/json");

        InputStream inputStream = conn.getInputStream();

        return mapper.readValue(inputStream, GencodeResult.class);
    }

    private String getAddressFromLocalization(LocalizationDTO l) {
        String s = "";

        if (l.getAddress() != null) {
            s += l.getAddress() + ",";
        }

        if (l.getHouseNumber() != null) {
            s += l.getHouseNumber() + ",";
        }

        if (l.getFlatNumber() != null) {
            s += l.getFlatNumber() + ",";
        }

        if (l.getZipCode() != null) {
            s += l.getZipCode();
            if (l.getCity() != null) {
                s += "/" + l.getCity();
            }
        }

        return s;
    }

    public DistanceMatrixResult getDistanceBetweenTwoLocations(Double posXLeft, Double posYLeft, Double posXRight, Double posYRight) throws IOException {
        String origins = "origins=" + posXLeft + "," + posYLeft;
        String destinations = "&destinations=" + posXRight + "," + posYRight;
        String key = "&key=" + geocodeKey;
        String urlString = distanceMatrixUrl + origins + destinations + key;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "application/json");

        InputStream inputStream = conn.getInputStream();


        return mapper.readValue(inputStream, DistanceMatrixResult.class);
    }
}
