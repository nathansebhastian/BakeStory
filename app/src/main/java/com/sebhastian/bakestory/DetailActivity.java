package com.sebhastian.bakestory;

import android.content.Intent;
import android.os.Bundle;
;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sebhastian.bakestory.adapter.StepAdapter;
import com.sebhastian.bakestory.model.Recipe;
import com.sebhastian.bakestory.model.Step;
import com.sebhastian.bakestory.widget.RecipeWidgetService;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class DetailActivity extends AppCompatActivity implements StepAdapter.Callbacks {
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_widget:
                Bundle selectedRecipeBundle = getIntent().getExtras();
                Recipe recipe = selectedRecipeBundle.getParcelable("RECIPE_OBJ");
                RecipeWidgetService.startBakingService(this, recipe);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final DetailActivityFragment fragment = new DetailActivityFragment();
        Bundle selectedRecipeBundle = getIntent().getExtras();
        fragment.setArguments(selectedRecipeBundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void watch(Step step, int position) {
        Bundle selectedRecipeBundle = new Bundle();
        selectedRecipeBundle.putParcelable("STEP_OBJ",step);

        if (findViewById(R.id.fragment_container2)!=null){
            final StepFragment fragment2 = new StepFragment();
            fragment2.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment2)
                    .commit();
        }
        else{
            final Intent i = new Intent(this, StepActivity.class);
            i.putExtra("STEP_OBJ", step);
            startActivity(i);
        }
    }
}
