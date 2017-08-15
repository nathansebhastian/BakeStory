package com.sebhastian.bakestory.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebhastian.bakestory.R;
import com.sebhastian.bakestory.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final ArrayList<Recipe> mRecipes;
    private final Callbacks mCallbacks;

    public RecipeAdapter(ArrayList<Recipe> recipes, Callbacks callbacks) {
        mRecipes = recipes;
        mCallbacks = callbacks;
    }

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;
    }

    public interface Callbacks {
        void read(Recipe recipe, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        final Context context = holder.mView.getContext();

        holder.mRecipe = recipe;
        holder.mRecipeTitleView.setText(recipe.getName());
        holder.mRecipeServingsView.setText(String.valueOf(recipe.getServings()));

        switch (recipe.getName()){
            case "Nutella Pie":
                if(!recipe.getImage().isEmpty()) {

                    Picasso.with(context).
                            load(recipe.getImage())
                            .placeholder(R.drawable.nutellapie)
                            .error(R.drawable.nutellapie)
                            .into(holder.mRecipeImageView);
                } else {
                    Picasso.with(context).
                            load(R.drawable.nutellapie)
                            .into(holder.mRecipeImageView);
                }
                break;
            case "Brownies":
                if(!recipe.getImage().isEmpty()) {

                    Picasso.with(context).
                            load(recipe.getImage())
                            .placeholder(R.drawable.brownies)
                            .error(R.drawable.brownies)
                            .into(holder.mRecipeImageView);
                } else {
                    Picasso.with(context).
                            load(R.drawable.brownies)
                            .into(holder.mRecipeImageView);
                }
                break;
            case "Yellow Cake":
                if(!recipe.getImage().isEmpty()) {

                    Picasso.with(context).
                            load(recipe.getImage())
                            .placeholder(R.drawable.yellowcake)
                            .error(R.drawable.yellowcake)
                            .into(holder.mRecipeImageView);
                } else {
                    Picasso.with(context).
                            load(R.drawable.yellowcake)
                            .into(holder.mRecipeImageView);
                }
                break;
            case "Cheesecake":
                if(!recipe.getImage().isEmpty()) {

                    Picasso.with(context).
                            load(recipe.getImage())
                            .placeholder(R.drawable.cheese)
                            .error(R.drawable.cheese)
                            .into(holder.mRecipeImageView);
                } else {
                    Picasso.with(context).
                            load(R.drawable.cheese)
                            .into(holder.mRecipeImageView);
                }
                break;
            default:
                if(!recipe.getImage().isEmpty()) {

                    Picasso.with(context).
                            load(recipe.getImage())
                            .placeholder(R.drawable.cake)
                            .error(R.drawable.cake)
                            .into(holder.mRecipeImageView);
                } else {
                    Picasso.with(context).
                            load(R.drawable.cake)
                            .into(holder.mRecipeImageView);
                }
                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.read(recipe, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void add(List<Recipe> recipes) {
        mRecipes.clear();
        mRecipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        @BindView(R.id.recipe_title)
        TextView mRecipeTitleView;
        @BindView(R.id.text_serving)
        TextView mRecipeServingsView;
        @BindView(R.id.recipe_image)
        ImageView mRecipeImageView;
        public Recipe mRecipe;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

}
