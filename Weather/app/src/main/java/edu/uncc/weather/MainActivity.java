package edu.uncc.weather;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements CitiesFragment.ICityOperations,CurrentWeatherFragment.ICurrentWeatherOperations {

    public static final String apiKey = "592eb8353bf282e4dc6955324e975005";
    public static final String TAG="HW06";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().
                add(R.id.rootView,new CitiesFragment(),"cities").commit();
    }



    @Override
    public void gotoCurrentWather(DataService.City city) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        CitiesFragment fragment =
                (CitiesFragment) fragmentManager.findFragmentByTag("cities");

        if(fragment != null)
        {
            //Data.CurrentWeather currentWeather = fragment.getCurrentWeatherData(city);
            getSupportFragmentManager().beginTransaction().replace(R.id.rootView,   new CurrentWeatherFragment(city),"current_weather").addToBackStack(null).commit();
        }
        else
        {
            Log.d(TAG, "No Current Weather Data Found: ");
        }


    }

    @Override
    public void gotoForecast(DataService.City city) {

        getSupportFragmentManager().beginTransaction().replace(R.id.rootView,   new WeatherForecastFragment(city),"weather_forecast").addToBackStack(null).commit();
    }
}