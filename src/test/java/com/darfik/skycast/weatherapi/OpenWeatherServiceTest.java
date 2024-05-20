package com.darfik.skycast.weatherapi;

import com.darfik.skycast.util.json.LocationJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OpenWeatherServiceTest {
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    public void setUp() {
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
    }

    @Test
    public void testGetLocationsByName() throws IOException, InterruptedException {
        String mockResponse = "[{\"name\":\"Pskov\",\"local_names\":{\"feature_name\":\"Pskov\",\"de\":\"Pskow\",\"sr\":\"Псков\",\"hr\":\"Pskov\",\"fr\":\"Pskov\",\"ce\":\"Псков\",\"nl\":\"Pskov\",\"bg\":\"Псков\",\"ascii\":\"Pskov\",\"el\":\"Πσκοφ\",\"da\":\"Pskov\",\"ru\":\"Псков\",\"ba\":\"Псков\",\"af\":\"Pskof\",\"zh\":\"普斯科夫\",\"cs\":\"Pskov\",\"az\":\"Pskov\",\"hy\":\"Պսկով\",\"pl\":\"Psków\",\"sk\":\"Pskov\",\"es\":\"Pskov\",\"fi\":\"Pihkova\",\"uk\":\"Псков\",\"br\":\"Pskov\",\"ka\":\"ფსკოვი\",\"cy\":\"Pskov\",\"ar\":\"بسكوف\",\"lt\":\"Pskovas\",\"sv\":\"Pskov\",\"be\":\"Пскоў\",\"la\":\"Pscovia\",\"ca\":\"Pskov\",\"et\":\"Pihkva\",\"cv\":\"Псков\",\"lv\":\"Pleskava\",\"en\":\"Pskov\",\"hu\":\"Pszkov\"},\"lat\":57.8173923,\"lon\":28.3343465,\"country\":\"RU\",\"state\":\"Pskov Oblast\"},{\"name\":\"Pskov\",\"local_names\":{\"pl\":\"Psków\",\"en\":\"Pskov\",\"ru\":\"Псков\",\"de\":\"Pskow\",\"et\":\"Pihkva\",\"fi\":\"Pihkova\",\"lv\":\"Pleskava\"},\"lat\":57.811616650000005,\"lon\":28.36540390200804,\"country\":\"RU\",\"state\":\"Pskov Oblast\"}]";
        when(mockHttpResponse.body()).thenReturn(mockResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        LocationJsonParser locationJsonParser = new LocationJsonParser();

        assertEquals("Pskov", locationJsonParser.parse(mockResponse)[0].getName());
    }
}
