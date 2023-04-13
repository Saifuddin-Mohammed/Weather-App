package edu.uncc.weather;


import java.util.List;

public class TempForecast {

    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
    public class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int sea_level;
        public int grnd_level;
        public int humidity;
        public double temp_kf;
    }

    public class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Clouds{
        public int all;
    }

    public class Wind{
        public double speed;
        public int deg;
        public double gust;
    }

    public class Sys{
        public String pod;
    }

    public class Data{
        public int dt;
        public Main main;
        public List<Weather> weather;
        public Clouds clouds;
        public Wind wind;
        public int visibility;
        public int pop;
        public Sys sys;
        public String dt_txt;
    }

    public class Coord{
        public double lat;
        public double lon;
    }

    public class City{
        public int id;
        public String name;
        public Coord coord;
        public String country;
        public int population;
        public int timezone;
        public int sunrise;
        public int sunset;
    }

    public class Root{
        public String cod;
        public int message;
        public int cnt;
        public List<Data> list;
        public City city;
    }




}
