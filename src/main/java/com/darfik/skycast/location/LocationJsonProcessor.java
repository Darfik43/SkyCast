package com.darfik.skycast.location;

import com.darfik.skycast.commons.factories.JsonParserFactory;
import com.darfik.skycast.commons.services.JsonParser;
import com.darfik.skycast.commons.services.JsonProcessor;

public class LocationJsonProcessor implements JsonProcessor {
    private final JsonParser<LocationJson> parser = JsonParserFactory.build("location");

    private String formatToOutput(LocationJson locationJson) {
        return "You are currently in "
                + locationJson.getLocationName() + " "
                + locationJson.getLatitude() + " "
                + locationJson.getLongitude();
    }


    @Override
    public String processJson(String json) {
        LocationJson responseJson = parser.parse(json);

        return formatToOutput(responseJson);
    }
}
