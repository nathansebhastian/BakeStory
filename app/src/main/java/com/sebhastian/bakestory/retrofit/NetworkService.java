package com.sebhastian.bakestory.retrofit;

import com.sebhastian.bakestory.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Yonathan Sebhastian on 8/9/2017.
 */

public interface NetworkService {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();

}
