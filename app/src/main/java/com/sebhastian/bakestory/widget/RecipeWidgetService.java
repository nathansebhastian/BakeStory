package com.sebhastian.bakestory.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.sebhastian.bakestory.model.Ingredient;
import com.sebhastian.bakestory.model.Recipe;

import java.util.List;

/**
 * Created by Yonathan Sebhastian on 8/15/2017.
 */

public class RecipeWidgetService extends IntentService {
    private static String LOG_TAG = RecipeWidgetService.class.getSimpleName();

    public static String FROM_ACTIVITY_RECIPE ="FROM_ACTIVITY_RECIPE";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startBakingService(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(FROM_ACTIVITY_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Recipe selectedRecipe = intent.getExtras().getParcelable(FROM_ACTIVITY_RECIPE);
        handleActionUpdateWidget(selectedRecipe);
    }

    private void handleActionUpdateWidget(Recipe selectedRecipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));

        List<Ingredient> ingredients = selectedRecipe.getIngredients();
        String recipeName = selectedRecipe.getName();

        RecipeWidget.updateRecipeWidgets(this, appWidgetManager,
                recipeName, ingredients, appWidgetIds);
    }
}
