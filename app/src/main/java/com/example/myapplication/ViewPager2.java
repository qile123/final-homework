package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPager2 extends AppCompatActivity implements View.OnClickListener{
    private MyAdapter2 mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager2);

        Button button = findViewById(R.id.btn_upload);
        button.setTextSize(18);
        findViewById(R.id.btn_VP2to1).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.RV_covers2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter2();
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
            case R.id.btn_VP2to1:
                Log.d("URL", "2to1");
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
