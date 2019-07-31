package com.example.movieapp.model.VideoModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoDetailResult> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoDetailResult> getResults() {
        return results;
    }

    public void setResults(List<VideoDetailResult> results) {
        this.results = results;
    }
}

