package edu.uncc.weather;
/* HW 06
 * File: CitiesFragment.java
 * Group 05 - Saifuddin Mohammed, Juhi Jadhav
 */
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitiesFragment extends Fragment implements CitiesAdapter.ICitiesData {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG="HW06";

    private final OkHttpClient client = new OkHttpClient();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CitiesAdapter adapter;
    DataService.CurrentWeather currentWeather;

    public CitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CitiesFragment newInstance(String param1, String param2) {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        

        getActivity().setTitle("Cities");
        View view =  inflater.inflate(R.layout.fragment_cities, container, false);

        recyclerView = view.findViewById(R.id.citiesView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CitiesAdapter(DataService.cities, (CitiesAdapter.ICitiesData) this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }

    @Override
    public void goToCurrentWeather(DataService.City city) {


        Log.d(TAG, "goToCurrentWeather: "+ city.getCity());
        IcityOperations.gotoCurrentWather(city);


    }

    public DataService.CurrentWeather getCurrentWeatherData(DataService.City city)
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






                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                currentWeather = obj;



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

        return currentWeather;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CitiesFragment.ICityOperations)
        {
            IcityOperations = (CitiesFragment.ICityOperations)context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "Must impl listner");
        }
    }


    CitiesFragment.ICityOperations IcityOperations ;
    public interface ICityOperations
    {

        void gotoCurrentWather(DataService.City city);

    }
}