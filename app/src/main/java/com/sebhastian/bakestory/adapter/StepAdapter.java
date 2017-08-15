package com.sebhastian.bakestory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebhastian.bakestory.R;
import com.sebhastian.bakestory.model.Recipe;
import com.sebhastian.bakestory.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private final List<Step> mSteps;
    private final Callbacks mCallbacks;

    public StepAdapter(ArrayList<Step> steps, Callbacks callbacks) {
        mSteps = steps;
        mCallbacks = callbacks;
    }


    public void add(List<Step> steps) {
        mSteps.clear();
        mSteps.addAll(steps);
        notifyDataSetChanged();
    }

    public interface Callbacks {
        void watch(Step step, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Step step = mSteps.get(position);
        final Context context = holder.mView.getContext();

        holder.mStep = step;
        if (step.getId()>0){
            if (step.getId()<10){
                holder.mStepListIDView.setText("0"+step.getId().toString()+". ");
            }
            else{
                holder.mStepListIDView.setText(step.getId().toString()+". ");
            }
        }
        holder.mStepListView.setText(step.getShortDescription());

        if (step.getVideoURL().isEmpty()){
            Picasso.with(context).
                    load(R.drawable.no_video)
                    .into(holder.mStepListImageView);
        }
        else {
            Picasso.with(context).
                    load(R.drawable.video)
                    .into(holder.mStepListImageView);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.watch(step, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        @BindView(R.id.item_step_list_desc)
        TextView mStepListView;
        @BindView(R.id.item_step_list_image)
        ImageView mStepListImageView;
        @BindView(R.id.item_step_list_id)
        TextView mStepListIDView;
        public Step mStep;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
