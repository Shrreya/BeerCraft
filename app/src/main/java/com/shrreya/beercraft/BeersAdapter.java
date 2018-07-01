package com.shrreya.beercraft;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.MyViewHolder> implements Filterable{

    private List<Beer> beersList;
    private List<Beer> beersListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, style, abv;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            style = view.findViewById(R.id.style);
            abv = view.findViewById(R.id.abv);
        }
    }


    public BeersAdapter(List<Beer> beersList) {
        this.beersList = beersList;
        this.beersListFiltered = beersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beer_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Beer beer = beersListFiltered.get(position);
        holder.name.setText(beer.getName());
        holder.style.setText(beer.getStyle());
        holder.abv.setText(beer.getAbv());
    }

    @Override
    public int getItemCount() {
        return beersListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String query = charSequence.toString();

                if (query.isEmpty()) {
                    beersListFiltered = beersList;
                } else {
                    List<Beer> filteredList = new ArrayList<>();
                    for (Beer beer : beersList) {

                        if (beer.getName().toLowerCase().contains(query.toLowerCase()) ||
                                beer.getStyle().contains(query)) {
                            filteredList.add(beer);
                        }
                    }

                    beersListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = beersListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                beersListFiltered = (List<Beer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
