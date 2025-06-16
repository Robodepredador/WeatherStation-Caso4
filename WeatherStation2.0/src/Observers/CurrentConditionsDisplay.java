package Observers;
import Model.*;

public class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;
    private boolean active;
    private final WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.active = false;
    }

    // Resto de implementación...
    @Override
    public void update(float temperature, float humidity, float pressure, float aqi) {
        if (active) {
            this.temperature = temperature;
            this.humidity = humidity;
        }
    }

    @Override
    public String getDisplayText() {
        return active ? String.format("Condiciones: %.1f°C, Humedad: %.1f%%", temperature, humidity) : "";
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
