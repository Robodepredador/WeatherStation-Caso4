package Observers;

import Model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDisplay implements Observer {
    private float lastTemperature;
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float sumTemps = 0;
    private int updateCount = 0;
    private boolean active;
    private WeatherData weatherData;

    public StatisticsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.active = false;
    }
    @Override
    public void update(float temperature, float humidity, float pressure, float aqi) {
        if (active) {
            lastTemperature = temperature;

            // Actualizar máx/mín
            maxTemp = Math.max(maxTemp, temperature);
            minTemp = Math.min(minTemp, temperature);

            // Acumular para promedio (sin duplicados)
            sumTemps += temperature;
            updateCount++;
        }
    }

    @Override
    public String getDisplayText() {
        if (!active || updateCount == 0) return "";

        float avg = sumTemps / updateCount;
        return String.format("Estadísticas: Prom=%.1f°C, Máx=%.1f°C, Mín=%.1f°C",
                avg, maxTemp, minTemp);
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
