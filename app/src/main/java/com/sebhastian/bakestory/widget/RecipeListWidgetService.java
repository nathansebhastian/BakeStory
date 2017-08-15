package com.sebhastian.bakestory.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sebhastian.bakestory.R;
import com.sebhastian.bakestory.model.Ingredient;

import java.util.List;

import static com.sebhastian.bakestory.widget.RecipeWidget.ingredientList;

/**
 * Created by Yonathan Sebhastian on 8/15/2017.
 */

public class RecipeListWidgetService extends RemoteViewsService {
    List<Ingredient> remoteViewIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public RecipeRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngredientsList = ingredientList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return remoteViewIngredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_row);

            views.setTextViewText(R.id.recipe_ingredient_name, remoteViewIngredientsList.get(position).getIngredient());
            views.setTextViewText(R.id.recipe_ingredient_quantity,
                    String.valueOf(remoteViewIngredientsList.get(position).getQuantity())
                            +" "+remoteViewIngredientsList.get(position).getMeasure());

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.recipe_ingredient_row, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }




    }
}
