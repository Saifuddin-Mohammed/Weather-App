package edu.uncc.weather;
/* HW 06
 * File: CurrentWeatherFragment.java
 * Group 05 - Saifuddin Mohammed, Juhi Jadhav
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
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
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG="HW06";

    private final OkHttpClient client = new OkHttpClient();

    Button checkForecat;
    DataService.City city;
    TextView city_country;
    TextView temp;
    TextView tempmax;
    TextView tempmin;
    TextView desc;
    TextView hum;
    TextView windseed;
    TextView winddegree;
    TextView cloudiness;
    ImageView icon;
    DataService.CurrentWeather currentWeather;
    // TODO: Rename and change types of parameters
    private DataService.CurrentWeather mParam1;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    public CurrentWeatherFragment(DataService.City city) {
        if(city !=null)
        {
            this.city=city;
            getCurrentWeatherData(city);
        }
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CurrentWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentWeatherFragment newInstance(DataService.CurrentWeather param1) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DataService.CurrentWeather) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    public void getCurrentWeatherData(DataService.City city)
    {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
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
                        DataService.CurrentWeather obj = gson.fromJson(jsonObject.toString(), DataService.CurrentWeather.class);
                        Log.d(TAG, "obj =  "+obj.weather.get(0).description);
                        mParam1=obj;





                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {



                                List<DataService.Weather >weathers = (List<DataService.Weather>) mParam1.weather;
                                DataService.Weather w = weathers.get(0);
                                String descdata = w.description;


                                city_country.setText(city.getCity() + ","+city.getCountry());
                                temp.setText(String.valueOf(mParam1.main.temp)+" F");
                                tempmax.setText(String.valueOf(mParam1.main.temp_max) + " F");
                                tempmin.setText(String.valueOf(mParam1.main.temp_min) + " F");
                                desc.setText(descdata);
                                hum.setText(String.valueOf(mParam1.main.humidity)+" %");
                                windseed.setText(String.valueOf(mParam1.wind.speed)+ " miles/hr");
                                winddegree.setText(String.valueOf(mParam1.wind.deg)+" degrees");
                                cloudiness.setText(String.valueOf(mParam1.clouds.all + "%"));

                                String imageUri = "https://openweathermap.org/img/wn/"+mParam1.weather.get(0).icon+"@2x.png";

                                Picasso.get().load(imageUri).into(icon);



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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mParam1 !=null)
        {

            city_country.setText(city.getCity() + ","+city.getCountry());

            temp.setText(String.valueOf(mParam1.main.temp)+ " F");
            tempmax.setText(String.valueOf(mParam1.main.temp_max)+ " F");
            tempmin.setText(String.valueOf(mParam1.main.temp_min)+ " F");
            desc.setText(String.valueOf(mParam1.weather.get(0).description));
            hum.setText(String.valueOf(mParam1.main.humidity)+ " %");
            windseed.setText(String.valueOf(mParam1.wind.speed)+ " miles/hr");
            winddegree.setText(String.valueOf(mParam1.wind.deg)+ " degrees");
            cloudiness.setText(String.valueOf(mParam1.clouds.all + "%"));

            String imageUri = "https://openweathermap.org/img/wn/"+mParam1.weather.get(0).icon+"@2x.png";

            Picasso.get().load(imageUri).into(icon);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Current Weather");
        View view =  inflater.inflate(R.layout.fragment_current_weather, container, false);
        checkForecat = view.findViewById(R.id.buttonCheckForecast);
        temp = view.findViewById(R.id.textViewTemp);
        tempmax = view.findViewById(R.id.textViewTempMax);
        tempmin = view.findViewById(R.id.textViewTempMin);
        desc = view.findViewById(R.id.textViewDesc);
        hum = view.findViewById(R.id.textViewHumidity);
        windseed = view.findViewById(R.id.textViewWindSpeed);
        winddegree = view.findViewById(R.id.textViewWindDegree);
        cloudiness = view.findViewById(R.id.textViewCloudiness);
        city_country= view.findViewById(R.id.textViewCityName);
        icon = view.findViewById(R.id.imageViewWeatherIcon);

        checkForecat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Check Forecast Clicked ");
                IcurrentWeatherOperations.gotoForecast(city);

            }
        });

        return  view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CurrentWeatherFragment.ICurrentWeatherOperations)
        {
            IcurrentWeatherOperations = (CurrentWeatherFragment.ICurrentWeatherOperations)context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "Must impl listner");
        }
    }


    CurrentWeatherFragment.ICurrentWeatherOperations IcurrentWeatherOperations ;

    public interface ICurrentWeatherOperations
    {

        void gotoForecast(DataService.City city);

    }
}