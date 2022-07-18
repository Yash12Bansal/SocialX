package com.example.social;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.social.Models.NewsApiResponse;
import com.example.social.Models.NewsHeadlines;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter adapter;

    SearchView searchView;
    ProgressDialog progressDialog;
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching articles related to "+query);
                progressDialog.show();
                RequestHandler requestHandler=new RequestHandler(HomeActivity.this);
                requestHandler.getNewsHeadlines(listener,"general",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Fetching news articles...");
        progressDialog.show();

        RequestHandler requestHandler=new RequestHandler(this);
        requestHandler.getNewsHeadlines(listener,"general",null);


    }
    private final OnFetchDataListener<NewsApiResponse>listener=new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(HomeActivity.this,"No results related to your query",Toast.LENGTH_LONG).show();
            }

            showNews(list);
            progressDialog.dismiss();

        }

        @Override
        public void onError(String message) {
            Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_LONG).show();
            Log.e(TAG,"thsis is the meaafda whchis rerqqqq"+message.toString());

        }
    };

    private void showNews(List<NewsHeadlines> list) {


        recyclerView=findViewById(R.id.recycler_main);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter=new CustomAdapter(this,list);
        Log.e(TAG, "showNews: fdfdklfjdklfjdklfjdfkldjfdadapafppeprerpeprnah he bay ky akar raha haintoa fnanananna");
        recyclerView.setAdapter(adapter);
    }
}