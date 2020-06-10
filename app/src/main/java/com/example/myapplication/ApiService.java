package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // https://beiyou.bytedance.com/api/invoke/video/invoke/video
    @GET("invoke/video/invoke/video")
    Call<List<VideoMessage>> getArticles();
}