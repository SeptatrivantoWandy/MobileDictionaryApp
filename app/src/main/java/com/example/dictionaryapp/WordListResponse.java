package com.example.dictionaryapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordListResponse {

    @SerializedName("word")
    @Expose
    public String word;
    @SerializedName("definitions")
    @Expose
    public List<WordResponse> definitions = null;

    public WordListResponse(String word, List<WordResponse> definitions) {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<WordResponse> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<WordResponse> definitions) {
        this.definitions = definitions;
    }
}
