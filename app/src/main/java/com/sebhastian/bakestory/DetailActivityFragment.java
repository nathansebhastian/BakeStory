package com.sebhastian.bakestory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sebhastian.bakestory.adapter.StepAdapter;
import com.sebhastian.bakestory.model.Ingredient;
import com.sebhastian.bakestory.model.Recipe;
import com.sebhastian.bakestory.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class DetailActivityFragment extends Fragment {
    private final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    public Recipe mRecipe;
    private StepAdapter mStepAdapter;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    @BindView(R.id.recipe_detail_ingredient_heading)
    TextView mRecipeHeadView;
    @BindView(R.id.recipe_details_ingredients)
    TextView mRecipeDetailsIngredientsView;
    @BindView(R.id.recipe_detail_recycler_view)
    RecyclerView mRecyclerViewStepListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable("RECIPE_OBJ");
        mRecipeHeadView.setText(mRecipe.getName());
        if (mRecipe != null){

//            for ingredients
            ingredients = mRecipe.getIngredients();
            mRecipeDetailsIngredientsView.append("Ingredients list:");
            for (Ingredient ingredient : ingredients){
                mRecipeDetailsIngredientsView.append("\n\u2022  "+ingredient.getIngredient()+" ("+ingredient.getQuantity().toString()+" "+ingredient.getMeasure()+")");
            }

//            for steps
            steps = mRecipe.getSteps();
            mStepAdapter = new StepAdapter(new ArrayList<Step>(), (DetailActivity)getActivity());
            mRecyclerViewStepListView.setAdapter(mStepAdapter);
            mStepAdapter.add(mRecipe.getSteps());
        }
        return rootView;

    }
}
