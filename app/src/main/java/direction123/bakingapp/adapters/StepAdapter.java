package direction123.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import direction123.bakingapp.R;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

/**
 * Created by fangxiangwang on 8/11/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.stepViewHolder> {
    private List<Step> mstepList;
    private final stepAdapterOnClickHandler mClickHandler;


    public interface stepAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class stepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mIdView;
        public TextView mShortDescriptionView;

        public stepViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.step_id);
            mShortDescriptionView = (TextView) view.findViewById(R.id.step_short_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = mstepList.get(adapterPosition);
            mClickHandler.onClick(step);
        }
    }

    public StepAdapter(stepAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @Override
    public int getItemCount() {
        if(mstepList != null)
            return mstepList.size();
        return 0;
    }

    @Override
    public stepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.step_list_item, parent, shouldAttachToParentImmediately);
        return new stepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(stepViewHolder holder, int position) {
        Step step = mstepList.get(position);

        TextView idView = holder.mIdView;
        String name = String.valueOf(step.getId());
        idView.setText(name + ". ");

        TextView servingView = holder.mShortDescriptionView;
        String shortDescription = step.getShortDescription();
        servingView.setText(shortDescription);
    }

    public void setstepList(List<Step> stepList){
        this.mstepList = stepList;
        notifyDataSetChanged();
    }

    public List<Step> getStepList() {
        return mstepList;
    }
}
