package com.yessumtorah.brandeetsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrandeetsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BrandeetsAdapter mBrandsAdapter;
    private ArrayList<Brand> mBrandsList;
    private RequestQueue mRequestQueue;
    private ProgressBar progressBar;
    private final String TAG = "BrandeetsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandeets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please log-in in order to upload new Brandeet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBrandsList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();
    }

    private void parseJSON() {

        String URL = "http://brandeets.herokuapp.com/brands";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "JSONArray length is: " + response.length());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject brand = response.getJSONObject(i);

                                String brandName = brand.getString("name");
                                String ext = brand.getString("ext");
                                String price = brand.getString("price");

                                mBrandsList.add(new Brand(brandName, ext, price));
                            }
                            mBrandsAdapter = new BrandeetsAdapter(BrandeetsActivity.this, mBrandsList);
                            mRecyclerView.setAdapter(mBrandsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}
