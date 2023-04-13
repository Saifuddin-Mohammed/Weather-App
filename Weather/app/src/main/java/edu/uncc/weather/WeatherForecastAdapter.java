package edu.uncc.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherForecastAdapter extends  RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>{


    public static ArrayList<Forecast.ForecastData> forecasts;
    IWeatherData IweatherData;



    public WeatherForecastAdapter(ArrayList<Forecast.ForecastData> forecasts, IWeatherData IweatherData) {
        this.forecasts = forecasts;
        this.IweatherData =IweatherData;

    }




    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast,parent,false);
        WeatherForecastViewHolder contact_holder= new WeatherForecastViewHolder(view,IweatherData);
        return contact_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastViewHolder holder, int position) {

        Forecast.ForecastData fdata = forecasts.get(position);

        //holder.cityCountry.setText(fdata.city + ","+fdata.country);
        holder.date.setText(fdata.dt_txt);

        holder.temp.setText(fdata.temp + " F");
        holder.tempmin.setText( "Min: "+ fdata.tempmin+" F");
        holder.tempmax.setText("Max: "+fdata.tempmax+" F");
        holder.hum.setText("Humidity: "+fdata.humidity + " %");
        holder.desc.setText(fdata.desc);

        String imageUri = "https://openweathermap.org/img/wn/"+fdata.icon_id+"@2x.png";

        Picasso.get().load(imageUri).into(holder.weather_icon);






    }

    @Override
    public int getItemCount () {
        return forecasts.size();
    }

    public static class WeatherForecastViewHolder extends RecyclerView.ViewHolder {


        TextView date;
        TextView temp;
        TextView tempmin;
        TextView tempmax;
        TextView hum;
        TextView desc;
        ImageView weather_icon;




        public WeatherForecastViewHolder(@NonNull View itemView, IWeatherData IweatherData) {
            super(itemView);

            date = itemView.findViewById(R.id.date_id);
            temp = itemView.findViewById(R.id.forecast_temp_id);
            tempmax = itemView.findViewById(R.id.forecast_tempmax_id);
            tempmin = itemView.findViewById(R.id.forecast_tempmin_id);
            hum = itemView.findViewById(R.id.forecast_humidity_id);
            desc = itemView.findViewById(R.id.forecast_description_id);
            weather_icon = itemView.findViewById(R.id.icon_forecast_id);




        }
    }

    interface IWeatherData {


    }
}
