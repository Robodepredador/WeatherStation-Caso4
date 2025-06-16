import Model.*;
import Observers.*;
import Controller.*;
import View.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Usamos invokeLater para asegurar que la UI se cree en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Configurar Look and Feel para mejor apariencia
                setLookAndFeel();

                // 2. Crear el modelo (WeatherData)
                WeatherData weatherData = new WeatherData();

                // 3. Crear el controlador (inyectando el modelo)
                WeatherController controller = new WeatherController(weatherData);

                // 4. Crear la vista (inyectando el controlador)
                WeatherView dashboard = new WeatherView(controller);

                // 5. Configurar y mostrar la ventana principal
                configureMainWindow(dashboard);

            } catch (Exception e) {
                showFatalError(e);
            }
        });
    }

    private static void setLookAndFeel() {
        try {
            // Usar el look and feel del sistema para mejor integración
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar el Look and Feel: " + e.getMessage());
        }
    }

    private static void configureMainWindow(WeatherView dashboard) {
        // Configuración adicional de la ventana principal
        dashboard.setLocationRelativeTo(null); // Centrar en pantalla
        dashboard.setResizable(true);
        dashboard.setVisible(true);

        // Mostrar mensaje de bienvenida
        JOptionPane.showMessageDialog(dashboard,
                "Bienvenido al Sistema Meteorológico\n\n"
                        + "1. Ingrese los datos meteorológicos\n"
                        + "2. Presione 'Actualizar Mediciones'\n"
                        + "3. Vea los resultados en los paneles",
                "Bienvenida",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showFatalError(Exception e) {
        // Manejo de errores críticos durante la inicialización
        JOptionPane.showMessageDialog(null,
                "Error crítico al iniciar la aplicación:\n" + e.getMessage() + "\n\n"
                        + "Detalles técnicos:\n" + e.toString(),
                "Error Fatal",
                JOptionPane.ERROR_MESSAGE);

        System.exit(1);
    }

}