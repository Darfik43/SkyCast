package com.darfik.skycast.servlet;

import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.service.LocationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/search")
public class SearchServlet extends RenderServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LocationService locationService = new LocationService();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("searchQuery"));
            locationDTO = locationService.getLocationByName(locationDTO);
            locationDTO = locationService.getWeatherByCoordinates(locationDTO);

            req.setAttribute("location", locationDTO.getName());
            req.setAttribute("latitude", locationDTO.getLatitude());
            req.setAttribute("longitude", locationDTO.getLongitude());
            req.setAttribute("temperature", locationDTO.getTemperature());

            processTemplate("search_results.html", req, resp);
        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        }
    }
}
