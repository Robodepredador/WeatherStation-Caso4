package Observers;

import Model.*;

import javax.swing.*;

public class AQIDisplay implements Observer {
    private String aqiStatus = "No hay datos";
    private boolean active;
    private final WeatherData weatherData;

    public AQIDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.active = false;
    }

    @Override
    public void update(float temperature, float humidity, float pressure, float aqi) {
        if (active) {
            if (aqi <= 50) {
                aqiStatus = String.format("AQI: %.0f (Buena)", aqi);
            } else if (aqi <= 100) {
                aqiStatus = String.format("AQI: %.0f (Moderada)", aqi);
            } else if (aqi <= 150) {
                aqiStatus = String.format("AQI: %.0f (Poco saludable)", aqi);
            } else if (aqi <= 200) {
                aqiStatus = String.format("AQI: %.0f (Insalubre)", aqi);
            } else if (aqi <= 300) {
                aqiStatus = String.format("AQI: %.0f (Muy insalubre)", aqi);
            } else {
                aqiStatus = String.format("AQI: %.0f (Peligrosa)", aqi);
            }
        }
    }

    @Override
    public String getDisplayText() {
        return active ? aqiStatus : "";
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
