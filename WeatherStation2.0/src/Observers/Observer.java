package Observers;

public interface Observer {
    void update(float temperature, float humidity, float pressure, float aqi);

    String getDisplayText();

    void setActive(boolean active);

    boolean isActive();
}
