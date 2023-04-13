package edu.uncc.weather;
/* HW 06
 * File: WeatherForecastFragment.java
 * Group 05 - Saifuddin Mohammed, Juhi Jadhav
 */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment implements  WeatherForecastAdapter.IWeatherData{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG="HW06";
    private final OkHttpClient client = new OkHttpClient();

    // TODO: Rename and change types of parameters
    private Forecast mParam1;
    private String mParam2;
    public DataService.City city;
    final String country="",citydata="";

    public ArrayList<Forecast.ForecastData> forecasts= new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    WeatherForecastAdapter adapter;
    TextView country_name;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }
    public WeatherForecastFragment(DataService.City city) {

        if(city !=null)
        {
            this.city=city;
            getForecastData(city);
        }
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment WeatherForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherForecastFragment newInstance(Forecast param1) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Forecast) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    public void getForecastData(DataService.City city)
    {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("forecast")
                .addQueryParameter("q", city.getCity())
                .addQueryParameter("units", "imperial")
                .addQueryParameter("appid", MainActivity.apiKey)
                .build();
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Gson gson= new Gson();


                        JSONArray json_contacts = jsonObject.getJSONArray("list");



                        for (int i=0;i<json_contacts.length();i++)
                        {
                            JSONObject contact_cur = json_contacts.getJSONObject(i);

                            Forecast.ForecastData f = new Forecast.ForecastData();
                            JSONObject jdata = jsonObject.getJSONObject("city");
                            JSONObject mainData = contact_cur.getJSONObject("main");
                            JSONArray weatherData = contact_cur.getJSONArray("weather");
                            JSONObject w = weatherData.getJSONObject(0);



                            f.country = jdata.getString("country");

                            f.city = jdata.getString("name");
                            f.dt_txt =contact_cur.getString("dt_txt");

                            f.temp =String.valueOf(mainData.getString("temp"));
                            f.tempmin =String.valueOf(mainData.getString("temp_min"));
                            f.tempmax =String.valueOf(mainData.getString("temp_max"));
                            f.humidity =String.valueOf(mainData.getString("humidity"));
                            f.desc =String.valueOf(w.getString("description"));
                            f.icon_id=w.getString("icon");
                            forecasts.add(f);


                        }







                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(forecasts !=null && forecasts.size() >0)
                                {

                                    country_name.setText(forecasts.get(0).city + ","+forecasts.get(0).country);

                                }


                                adapter.notifyDataSetChanged();



                            }
                        });


                    }
                    catch (Exception ex)
                    {

                        Log.d(TAG, "API Failed: ");


                    }
                }
                else
                {

                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Weather Forecast");
        View view =  inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        recyclerView = view.findViewById(R.id.weather_forecaast_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        country_name =  view.findViewById(R.id.forecast_country_code_d2);


        adapter = new WeatherForecastAdapter(forecasts, (WeatherForecastAdapter.IWeatherData) this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }


}