package com.sebhastian.bakestory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sebhastian.bakestory.adapter.RecipeAdapter;
import com.sebhastian.bakestory.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.Callbacks {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void read(Recipe recipe, int position) {
        Bundle selectedRecipeBundle = new Bundle();
        selectedRecipeBundle.putParcelable("RECIPE_OBJ",recipe);

        final Intent i = new Intent(this, DetailActivity.class);
        i.putExtras(selectedRecipeBundle);
        startActivity(i);
    }
}
