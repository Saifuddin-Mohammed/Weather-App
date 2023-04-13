package edu.uncc.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {


        public ArrayList<DataService.City> cities;
        ICitiesData IcitiesData;


        @NonNull
        @Override
        public CitiesViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city,parent,false);
            CitiesViewHolder contact_holder= new CitiesViewHolder(view,IcitiesData);
            return contact_holder;
    }

        @Override
        public void onBindViewHolder (@NonNull CitiesViewHolder holder,int position){

            holder.city=cities.get(position);
            holder.cityCountry.setText(cities.get(position).getCity() + ","+ cities.get(position).getCountry());

    }

    public CitiesAdapter(ArrayList < DataService.City > cities, ICitiesData Icities) {
        this.IcitiesData = Icities;
        this.cities = cities;
    }


        @Override
        public int getItemCount () {
        return cities.size();
    }

        public static class CitiesViewHolder extends RecyclerView.ViewHolder {

           DataService.City city;
           TextView cityCountry;

            //IDeleteContactRecycler IdeleteContactRecycler;
            public CitiesViewHolder(@NonNull View itemView, ICitiesData IcitiesData) {
                super(itemView);
                cityCountry = itemView.findViewById(R.id.citiy_country_id);

//
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        IcitiesData.goToCurrentWeather(city);

                    }
                });




            }
        }

        interface ICitiesData {
            void goToCurrentWeather(DataService.City city);

        }
    }





