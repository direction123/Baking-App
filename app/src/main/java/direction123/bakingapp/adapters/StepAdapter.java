package direction123.bakingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import direction123.bakingapp.R;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

/**
 * Created by fangxiangwang on 8/11/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.stepViewHolder> {
    private Context mContext;
    private List<Step> mStepList;
    private final stepAdapterOnClickHandler mClickHandler;

    private int mSelectedPosition = 0;

    public interface stepAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class stepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mIdView;
        public TextView mShortDescriptionView;
        public LinearLayout mLinearLayout;

        public stepViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.step_id);
            mShortDescriptionView = (TextView) view.findViewById(R.id.step_short_description);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.step_item_linearLayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Updating old as well as new positions
            notifyItemChanged(mSelectedPosition);
            mSelectedPosition = getAdapterPosition();
            notifyItemChanged(mSelectedPosition);

            Step step = mStepList.get(mSelectedPosition);
            mClickHandler.onClick(step);
        }
    }

    public StepAdapter(Context context, stepAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    @Override
    public int getItemCount() {
        if(mStepList != null)
            return mStepList.size();
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
        Step step = mStepList.get(position);

        TextView idView = holder.mIdView;
        String name = String.valueOf(step.getId());
        idView.setText(name + ". ");

        TextView servingView = holder.mShortDescriptionView;
        String shortDescription = step.getShortDescription();
        servingView.setText(shortDescription);

        holder.mLinearLayout.setBackgroundColor(
                mSelectedPosition == position ?
                        ContextCompat.getColor(mContext, R.color.colorPrimaryLight):
                        ContextCompat.getColor(mContext, R.color.colorWhite)
        );
    }

    public void setStepList(List<Step> stepList){
        this.mStepList = stepList;
        notifyDataSetChanged();
    }
}
