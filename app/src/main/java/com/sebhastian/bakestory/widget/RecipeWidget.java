package com.sebhastian.bakestory.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sebhastian.bakestory.R;
import com.sebhastian.bakestory.model.Ingredient;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {
    static List<Ingredient> ingredientList;

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           String recipeName, List<Ingredient> ingredients, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, ingredients, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipeName,  List<Ingredient> ingredients, int appWidgetId) {

        ingredientList = ingredients;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//        views.removeAllViews(R.id.widget_list);
        views.setTextViewText(R.id.widget_text, recipeName);
//        views.setOnClickPendingIntent(R.id.recipe_widget_holder, pendingIntent);
        Intent intent = new Intent(context, RecipeListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

