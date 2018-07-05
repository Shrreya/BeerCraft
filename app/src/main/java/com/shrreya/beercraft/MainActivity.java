package com.shrreya.beercraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Beer> beerList = new ArrayList<>();
    private BeersAdapter beersAdapter;

    private RecyclerView recyclerView;
    private ImageView progress;
    private EditText search;
    private LinearLayout sort;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        progress = findViewById(R.id.progress);
        search = findViewById(R.id.search);
        sort = findViewById(R.id.sort);
        spinner = findViewById(R.id.spinner);

        Glide.with(this).asGif().load(R.drawable.beer).into(progress);

        beersAdapter = new BeersAdapter(beerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(beersAdapter);

        fetchBeerData();
        setupSort();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                beersAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupSort() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                beersAdapter.sort(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fetchBeerData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://starlord.hackerearth.com/beercraft")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String beerData = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray beerArray = new JSONArray(beerData);
                            for(int i = 0; i < beerArray.length(); i++) {
                                JSONObject beerObject = beerArray.getJSONObject(i);

                                String abv = beerObject.getString("abv");
                                String ibu = beerObject.getString("ibu");
                                int id = beerObject.getInt("id");
                                String name = beerObject.getString("name");
                                String style = beerObject.getString("style");
                                double ounces = beerObject.getDouble("ounces");

                                Beer beer = new Beer(abv, ibu, id, name, style, ounces);
                                beerList.add(beer);
                            }
                            beersAdapter.notifyDataSetChanged();
                            progress.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            sort.setVisibility(View.VISIBLE);
                            search.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
