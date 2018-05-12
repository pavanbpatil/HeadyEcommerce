package com.pavan.headyecommerce.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.Ranking;

import java.util.List;

public class DataResponse {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("rankings")
    @Expose
    private List<Ranking> rankings = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }
}