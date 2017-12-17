package hey.forecast.entity;

/**
 * Created by yhb on 17-12-16.
 */

public class City {
    private String name;
    private String weather;
    private String temperature;

    public City() {

    }

    public City(String name, String weather, String temperature) {
        this.name = name;
        this.weather = weather;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
