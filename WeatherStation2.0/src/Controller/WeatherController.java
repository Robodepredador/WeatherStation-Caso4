package Controller;

import Model.*;
import Observers.*;
import View.*;

public class WeatherController {
    private final WeatherData weatherData;

    // Observers
    private final CurrentConditionsDisplay currentObserver;
    private final StatisticsDisplay statsObserver;
    private final ForecastDisplay forecastObserver;
    private final AQIDisplay aqiObserver;
    private final AlertSystem alertSystem;

    public WeatherController(WeatherData weatherData) {
        this.weatherData = weatherData;

        // Inicializar observers
        this.currentObserver = new CurrentConditionsDisplay(weatherData);
        this.statsObserver = new StatisticsDisplay(weatherData);
        this.forecastObserver = new ForecastDisplay(weatherData);
        this.aqiObserver = new AQIDisplay(weatherData);
        this.alertSystem = new AlertSystem();

        // Registrar el sistema de alertas como observer permanente
        weatherData.registerObserver(alertSystem);
    }

    public void updateMeasurements(float temp, float humidity, float pressure, float aqi) {
        // Actualizar datos y notificar a TODOS los observers registrados
        weatherData.setMeasurements(temp, humidity, pressure, aqi);
    }

    public String getAlertMessage() {
        // Obtener mensaje actualizado del sistema de alertas
        return alertSystem.getDisplayText();
    }

    public void toggleConditionsDisplay() {
        currentObserver.setActive(!currentObserver.isActive());
        updateObserverRegistration(currentObserver);
    }

    public void toggleStatisticsDisplay() {
        statsObserver.setActive(!statsObserver.isActive());
        updateObserverRegistration(statsObserver);
    }

    public void toggleForecastDisplay() {
        forecastObserver.setActive(!forecastObserver.isActive());
        updateObserverRegistration(forecastObserver);
    }

    public void toggleAQIDisplay() {
        aqiObserver.setActive(!aqiObserver.isActive());
        updateObserverRegistration(aqiObserver);
    }

    private void updateObserverRegistration(Observer observer) {
        if (observer.isActive()) {
            weatherData.registerObserver(observer);
        } else {
            weatherData.removeObserver(observer);
        }
    }

    // Getters para estado de visualizaciones
    public boolean isConditionsDisplayActive() {
        return currentObserver.isActive();
    }

    public boolean isStatisticsDisplayActive() {
        return statsObserver.isActive();
    }

    public boolean isForecastDisplayActive() {
        return forecastObserver.isActive();
    }

    public boolean isAQIDisplayActive() {
        return aqiObserver.isActive();
    }

    // Getters para datos de visualización
    public String getCurrentConditions() {
        return currentObserver.getDisplayText();
    }

    public String getStatistics() {
        return statsObserver.getDisplayText();
    }

    public String getForecast() {
        return forecastObserver.getDisplayText();
    }

    public String getAQIInfo() {
        return aqiObserver.getDisplayText();
    }

    public boolean hasAlerts() {
        return !alertSystem.getDisplayText().equals("Sin alertas");
    }
}
