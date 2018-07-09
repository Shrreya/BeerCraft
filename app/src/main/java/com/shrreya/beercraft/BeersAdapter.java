package com.shrreya.beercraft;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.MyViewHolder> implements Filterable{

    private List<Beer> beersList;
    private List<Beer> beersListFiltered;

    private final int SORT_NAME = 1;
    private final int SORT_LOW_ABV = 2;
    private final int SORT_HIGH_ABV = 3;

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

    public void sort(int sortType) {
        switch(sortType) {
            case SORT_NAME:
                Collections.sort(beersListFiltered,  (b1, b2) -> b1.getName().compareTo(b2.getName()));
                break;
            case SORT_LOW_ABV:
                Collections.sort(beersListFiltered, (b1, b2) -> {
                    double abv1 = b1.getAbv().equals("") ? 0.0 : Double.parseDouble(b1.getAbv());
                    double abv2 = b2.getAbv().equals("") ? 0.0 : Double.parseDouble(b2.getAbv());
                    if (abv1 < abv2) {
                        return 1;
                    } else if (abv1 > abv2) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
                break;
            case SORT_HIGH_ABV:
                Collections.sort(beersListFiltered, (b1, b2) -> {
                    double abv1 = b1.getAbv().equals("") ? 1.0 : Double.parseDouble(b1.getAbv());
                    double abv2 = b2.getAbv().equals("") ? 1.0 : Double.parseDouble(b2.getAbv());
                    if (abv1 > abv2) {
                        return 1;
                    } else if (abv1 < abv2) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
                break;
        }
        notifyDataSetChanged();
    }
}
