package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyAdapter1 mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.RV_covers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter1();
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://beiyou.bytedance.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                apiService.getArticles().enqueue(new Callback<List<VideoMessage>>() {
                    @Override
                    public void onResponse(Call<List<VideoMessage>> call, Response<List<VideoMessage>> response) {
                        if (response.body() != null) {
                            Log.d("retrofit", Integer.toString(response.body().size()));
                            List<VideoMessage> videoMessages = response.body();
                            Log.d("retrofit", videoMessages.toString());
                            if (videoMessages.size() != 0) {
                                mAdapter.setContext(getApplicationContext());
                                mAdapter.setData(videoMessages);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

            @Override
            public void onFailure(Call<List<VideoMessage>> call, Throwable t) {
                Log.d("retrofit Fail", t.getMessage());
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Log.d("URL", "RecycleView to ViewPaper2");
                startActivity(new Intent(this, ViewPager2.class));
                break;
        }
    }
}
