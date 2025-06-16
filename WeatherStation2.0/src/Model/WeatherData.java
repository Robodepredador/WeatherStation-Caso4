package Model;
import Observers.*;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private float temp;
    private float humidity;
    private float pressure;
    private float aqi;
    private float lastPressure;

    @Override
    public void registerObserver(Observer o){
        observers.add(o);
    }


    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temp, humidity, pressure, aqi);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }

    public void setMeasurements(float temp, float humidity, float pressure, float aqi){
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.aqi = aqi;
        this.lastPressure = pressure;
        measurementsChanged();
    }

    public float getLastPressure(){
        return lastPressure;
    }
}
