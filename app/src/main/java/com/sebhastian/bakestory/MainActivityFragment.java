package com.sebhastian.bakestory;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sebhastian.bakestory.adapter.RecipeAdapter;
import com.sebhastian.bakestory.model.Recipe;
import com.sebhastian.bakestory.retrofit.NetworkService;
import com.sebhastian.bakestory.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class MainActivityFragment extends Fragment {

    static final String SAVED_RECIPES = "SAVED_RECIPES";

    @BindView(R.id.recipe_recycler)
    RecyclerView mRecyclerViewRecipeList;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_state_container)
    View emptyView;

    private NetworkService networkService;
    public RecipeAdapter mRecipeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        mRecipeAdapter =new RecipeAdapter(new ArrayList<Recipe>(), (MainActivity)getActivity());
        mRecyclerViewRecipeList.setAdapter(mRecipeAdapter);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SAVED_RECIPES)) {
                List<Recipe> recipes = savedInstanceState.getParcelableArrayList(SAVED_RECIPES);
                mRecipeAdapter.add(recipes);
                mRecyclerViewRecipeList.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        } else {
            if(isConnected()){
                networkService = RetrofitBuilder.Retrieve();
                final Call<ArrayList<Recipe>> recipe = networkService.getRecipe();

                @VisibleForTesting
                final SimpleIdlingResource idlingResource = (SimpleIdlingResource) ((MainActivity)getActivity()).getIdlingResource();
                if (idlingResource != null) {
                    idlingResource.setIdleState(false);
                }

                recipe.enqueue(new Callback<ArrayList<Recipe>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                        Integer statusCode = response.code();
                        Log.v("status code: ", statusCode.toString());

                        ArrayList<Recipe> recipes = response.body();

                        mRecipeAdapter.add(recipes);
                        mRecyclerViewRecipeList.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);

                        if (idlingResource != null) {
                            idlingResource.setIdleState(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                        Log.v("http fail: ", t.getMessage());
                    }
                });
            }
        }

        return rootView;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Recipe> recipes = mRecipeAdapter.getRecipes();
        if (recipes!=null && !recipes.isEmpty()){
            outState.putParcelableArrayList(SAVED_RECIPES, recipes);
        }
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || !activeNetworkInfo.isAvailable()) {
            mProgressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }
}
