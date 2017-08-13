package direction123.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import direction123.bakingapp.adapters.StepAdapter;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

public class StepListFragment extends Fragment implements StepAdapter.stepAdapterOnClickHandler{
    @BindView(R.id.recyclerview_steps) RecyclerView mRecyclerView;
    @BindView(R.id.ingredients_list) TextView ingredientsTextView;

    private StepAdapter mAdapter;
    public static final String RECIPE = "recipe";

    // Define a new interface OnStepClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }
    public static StepListFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(RECIPE, recipe);

        StepListFragment fragment = new StepListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, rootView);

        final int columns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new StepAdapter(getContext(), this);
        mRecyclerView.setAdapter(mAdapter);

        Recipe recipe = getArguments().getParcelable(RECIPE);
        if (recipe != null) {
            ingredientsTextView.setText(recipe.getIngredients());
            mAdapter.setStepList(recipe.getStepList());
        }
        return rootView;
    }

    @Override
    public void onClick(Step step) {
        mCallback.onStepSelected(step);
    }

}
