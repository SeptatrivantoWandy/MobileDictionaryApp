package com.example.dictionaryapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface herokuappService {
    @GET("words?q")
    Call<List<WordListResponse>> getWords();
}
