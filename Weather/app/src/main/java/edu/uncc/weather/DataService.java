package edu.uncc.weather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    public class Coord{
        public double lon;
        public double lat;
    }

    public class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    public class Wind{
        public double speed;
        public int deg;
        public double gust;
    }

    public class Clouds{
        public int all;
    }

    public class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public class CurrentWeather{
        public Coord coord;
        public List<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }
    static public final ArrayList<City> cities = new ArrayList<City>(){{
        add(new City("US","Charlotte"));
        add(new City("US","Chicago"));
        add(new City("US","New York"));
        add(new City("US","Miami"));
        add(new City("US","San Francisco"));
        add(new City("US","Baltimore"));
        add(new City("US","Houston"));
        add(new City("UK","London"));
        add(new City("UK","Bristol"));
        add(new City("UK","Cambridge"));
        add(new City("UK","Liverpool"));
        add(new City("AE","Abu Dhabi"));
        add(new City("AE","Dubai"));
        add(new City("AE","Sharjah"));
        add(new City("JP","Tokyo"));
        add(new City("JP","Kyoto"));
        add(new City("JP","Hashimoto"));
        add(new City("JP","Osaka"));
    }};

    static class City implements Serializable {
        private String country;
        private String city;

        public City(String country, String city) {
            this.country = country;
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "City{" +
                    "country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}
