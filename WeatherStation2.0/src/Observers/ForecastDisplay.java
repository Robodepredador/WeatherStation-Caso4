package Observers;

import Model.*;

import javax.swing.*;

public class ForecastDisplay implements Observer {
    private String forecast = "No hay datos";
    private boolean active;
    private float lastPressure;
    private final WeatherData weatherData;

    public ForecastDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.lastPressure = 1013.0f;
        this.active = false;
    }

    @Override
    public void update(float temperature, float humidity, float pressure, float aqi) {
        if (active) {
            if (pressure > lastPressure) {
                forecast = "Pronóstico: Mejorando el tiempo";
            } else if (pressure < lastPressure) {
                forecast = "Pronóstico: Empeorando el tiempo";
            } else {
                forecast = "Pronóstico: Sin cambios";
            }
            lastPressure = pressure;
        }
    }

    @Override
    public String getDisplayText() {
        return active ? forecast : "";
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
