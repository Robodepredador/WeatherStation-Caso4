package View;


import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WeatherView extends JFrame {
    private WeatherController controller;

    // Componentes del formulario
    private JTextField tempField;
    private JTextField humidityField;
    private JTextField pressureField;
    private JTextField aqiField;

    // Botones
    private JButton updateButton;
    private JButton conditionsButton;
    private JButton statsButton;
    private JButton forecastButton;
    private JButton aqiButton;

    // Paneles de visualización
    private JPanel conditionsPanel;
    private JPanel statsPanel;
    private JPanel forecastPanel;
    private JPanel aqiPanel;
    private JPanel alertPanel;
    private JLabel alertLabel;

    public WeatherView(WeatherController controller) {
        this.controller = controller;
        setupUI();
        setupListeners();
    }

    private void setupUI() {
        setTitle("Sistema Meteorológico - Observer Pattern");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel superior (formulario + botón actualizar)
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        // 2. Panel central (visualizaciones + botones de control)
        JPanel mainDisplayPanel = createMainDisplayPanel();
        add(mainDisplayPanel, BorderLayout.CENTER);

        // 3. Panel de alertas (inferior)
        setupAlertPanel();
        add(alertPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos Meteorológicos"));

        tempField = new JTextField();
        humidityField = new JTextField();
        pressureField = new JTextField();
        aqiField = new JTextField();

        panel.add(new JLabel("Temperatura (°C):"));
        panel.add(tempField);
        panel.add(new JLabel("Humedad (%):"));
        panel.add(humidityField);
        panel.add(new JLabel("Presión (hPa):"));
        panel.add(pressureField);
        panel.add(new JLabel("Índice AQI:"));
        panel.add(aqiField);

        // Panel contenedor para el formulario + botón actualizar
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);

        updateButton = new JButton("Actualizar Datos");
        container.add(updateButton, BorderLayout.EAST);

        return container;
    }

    private JPanel createMainDisplayPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Panel de visualizaciones (izquierda)
        JPanel displaysPanel = new JPanel();
        displaysPanel.setLayout(new BoxLayout(displaysPanel, BoxLayout.Y_AXIS));
        displaysPanel.setBorder(BorderFactory.createTitledBorder("Visualizaciones"));

        conditionsPanel = createDisplayPanel("Condiciones Actuales", "No hay datos disponibles");
        statsPanel = createDisplayPanel("Estadísticas", "No hay datos disponibles");
        forecastPanel = createDisplayPanel("Pronóstico", "No hay datos disponibles");
        aqiPanel = createDisplayPanel("Calidad del Aire", "No hay datos disponibles");

        displaysPanel.add(conditionsPanel);
        displaysPanel.add(statsPanel);
        displaysPanel.add(forecastPanel);
        displaysPanel.add(aqiPanel);

        // Panel de botones de control (derecha)
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Controles"));

        conditionsButton = new JButton("Agregar Condiciones");
        statsButton = new JButton("Agregar Estadísticas");
        forecastButton = new JButton("Agregar Pronóstico");
        aqiButton = new JButton("Agregar Calidad Aire");

        controlsPanel.add(Box.createVerticalStrut(10));
        controlsPanel.add(conditionsButton);
        controlsPanel.add(Box.createVerticalStrut(10));
        controlsPanel.add(statsButton);
        controlsPanel.add(Box.createVerticalStrut(10));
        controlsPanel.add(forecastButton);
        controlsPanel.add(Box.createVerticalStrut(10));
        controlsPanel.add(aqiButton);
        controlsPanel.add(Box.createVerticalGlue());

        mainPanel.add(displaysPanel, BorderLayout.CENTER);
        mainPanel.add(controlsPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createDisplayPanel(String title, String initialText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JLabel label = new JLabel(initialText);
        panel.add(label);
        panel.setVisible(false); // Inicialmente ocultos
        return panel;
    }

    private void setupAlertPanel() {
        alertPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        alertLabel = new JLabel("Sin alertas");
        alertPanel.add(alertLabel);
        alertPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        updateAlertStyle(false); // Estilo inicial sin alertas
    }

    private void updateAlertStyle(boolean hasAlerts) {
        alertPanel.setBackground(hasAlerts ? new Color(255, 200, 200) : Color.WHITE);
        alertLabel.setForeground(hasAlerts ? Color.RED : Color.BLACK);
        alertLabel.setFont(hasAlerts ? new Font("Arial", Font.BOLD, 12) : new Font("Arial", Font.PLAIN, 12));
    }

    private void setupListeners() {
        updateButton.addActionListener(this::handleUpdate);

        conditionsButton.addActionListener(e -> {
            controller.toggleConditionsDisplay();
            updateButtonState(conditionsButton, controller.isConditionsDisplayActive());
        });

        statsButton.addActionListener(e -> {
            controller.toggleStatisticsDisplay();
            updateButtonState(statsButton, controller.isStatisticsDisplayActive());
        });

        forecastButton.addActionListener(e -> {
            controller.toggleForecastDisplay();
            updateButtonState(forecastButton, controller.isForecastDisplayActive());
        });

        aqiButton.addActionListener(e -> {
            controller.toggleAQIDisplay();
            updateButtonState(aqiButton, controller.isAQIDisplayActive());
        });
    }

    private void handleUpdate(ActionEvent e) {
        try {
            // Obtener valores de los campos
            float temp = Float.parseFloat(tempField.getText());
            float humidity = Float.parseFloat(humidityField.getText());
            float pressure = Float.parseFloat(pressureField.getText());
            float aqi = Float.parseFloat(aqiField.getText());

            // Actualizar mediciones
            controller.updateMeasurements(temp, humidity, pressure, aqi);

            // Actualizar alertas
            String alertMessage = controller.getAlertMessage();
            alertLabel.setText(alertMessage);
            updateAlertStyle(!alertMessage.equals("Sin alertas"));

            // Actualizar visualizaciones
            updateDisplays();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateButtonState(JButton button, boolean isActive) {
        button.setBackground(isActive ? new Color(144, 238, 144) : UIManager.getColor("Button.background"));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    public void updateDisplays() {
        // Actualizar alertas
        alertLabel.setText(controller.getAlertMessage());
        alertPanel.setBackground(controller.hasAlerts() ? new Color(255, 200, 200) : Color.WHITE);

        // Actualizar solo las visualizaciones activas
        conditionsPanel.setVisible(controller.isConditionsDisplayActive());
        if (controller.isConditionsDisplayActive()) {
            ((JLabel)conditionsPanel.getComponent(0)).setText(controller.getCurrentConditions());
        }

        statsPanel.setVisible(controller.isStatisticsDisplayActive());
        if (controller.isStatisticsDisplayActive()) {
            ((JLabel)statsPanel.getComponent(0)).setText(controller.getStatistics());
        }

        forecastPanel.setVisible(controller.isForecastDisplayActive());
        if (controller.isForecastDisplayActive()) {
            ((JLabel)forecastPanel.getComponent(0)).setText(controller.getForecast());
        }

        aqiPanel.setVisible(controller.isAQIDisplayActive());
        if (controller.isAQIDisplayActive()) {
            ((JLabel)aqiPanel.getComponent(0)).setText(controller.getAQIInfo());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error de Entrada", JOptionPane.ERROR_MESSAGE);
    }


}
