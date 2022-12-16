package com.example.qualityoflifeinthecity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchCitiesAdapter extends
        RecyclerView.Adapter<SearchCitiesAdapter.SearchCityViewHolder> {

    interface ItemClickedListener{
        void onItemClicked(int position);
    }

    Context context;
    ArrayList<City> list;
    SearchCitiesAdapter.ItemClickedListener search_listener;

    public SearchCitiesAdapter(Context context, ArrayList<City> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchCitiesAdapter.SearchCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_cities_row,parent,false);
        return new SearchCitiesAdapter.SearchCityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCitiesAdapter.SearchCityViewHolder holder, int position) {
        holder.cityText.setText(list.get(position).city);
        holder.countryText.setText(list.get(position).country);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // inner class
    public class SearchCityViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView cityText;
        TextView countryText;

        public SearchCityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityText =  itemView.findViewById(R.id.searchCity);
            countryText =  itemView.findViewById(R.id.searchCountry);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            search_listener.onItemClicked( getAdapterPosition());

        }
    }

}
