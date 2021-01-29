package com.example.foodapiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Food> foods;
    private static  String JSON_URL="https://www.themealdb.com/api/json/v1/1/categories.php";
    private  static  String JSON_URL2="https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood";
    Adapter adapter;

    SearchView searchView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        foods=new ArrayList<>();
        //extractFoods();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        GetData getData=new GetData();
        getData.execute();

        searchView=findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void extractFoods() {
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject foodObject = response.getJSONObject(i);
                        Food food = new Food();
                        food.setFoodName(foodObject.getString("strCategory").toString());
                        food.setFoodId(foodObject.getString("idCategory").toString());
                        food.setFoodDesc(foodObject.getString("strCategoryDescription").toString());
                        food.setFoodImage(foodObject.getString("strCategoryThumb").toString());

                        foods.add(food);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse"+error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    public class  GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url=new URL(JSON_URL2);
                    urlConnection=(HttpURLConnection) url.openConnection();

                    InputStream inputStream=urlConnection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    int data=inputStreamReader.read();
                    while(data!=-1){
                        current += (char) data;
                        data=inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            //for URL 1
            /*try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("categories");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    Food food = new Food();
                    food.setFoodName(object.getString("strCategory").toString());
                    food.setFoodId(object.getString("idCategory").toString());
                    food.setFoodDesc(object.getString("strCategoryDescription").toString());
                    food.setFoodImage(object.getString("strCategoryThumb").toString());
                    foods.add(food);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            //for URL 2
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("meals");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    Food food = new Food();
                    food.setFoodName(object.getString("strMeal").toString());
                    //food.setFoodId(object.getString("idCategory").toString());
                    food.setFoodDesc(object.getString("idMeal").toString());
                    food.setFoodImage(object.getString("strMealThumb").toString());
                    foods.add(food);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter=new Adapter(getApplicationContext(),foods);
            recyclerView.setAdapter(adapter);
        }
    }

}