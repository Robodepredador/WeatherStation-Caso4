package Observers;

import Util.*;
import Model.*;

public class AlertSystem implements Observer {
    private String alertMessage = "Sin alertas";

    @Override
    public void update(float temperature, float humidity, float pressure, float aqi) {
        StringBuilder alerts = new StringBuilder();

        // Verificar umbrales de temperatura
        if (temperature > Constants.HIGH_TEMP_THRESHOLD) {
            alerts.append(String.format("Alerta! Temperatura alta: %.1f°C | ", temperature));
        }

        // Verificar umbrales de humedad
        if (humidity > Constants.HIGH_HUMIDITY_THRESHOLD) {
            alerts.append(String.format("Alerta! Humedad alta: %.1f%% | ", humidity));
        }

        // Verificar umbrales de calidad del aire
        if (aqi > Constants.HIGH_AQI_THRESHOLD) {
            alerts.append(String.format("Alerta! AQI elevado: %.0f", aqi));
        }

        // Actualizar mensaje (o mantener "Sin alertas")
        alertMessage = alerts.length() > 0 ? alerts.toString() : "Sin alertas";
    }

    @Override
    public String getDisplayText() {
        return alertMessage;
    }

    @Override
    public void setActive(boolean active) {
        // Siempre activo, no se puede desactivar
    }

    @Override
    public boolean isActive() {
        return true; // Siempre está activo
    }
}
