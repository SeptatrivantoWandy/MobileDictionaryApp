package com.example.dictionaryapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordResponse {

    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("definition")
    @Expose
    public String definition;

    public WordResponse(String imageUrl, String type, String definition) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.definition = definition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
