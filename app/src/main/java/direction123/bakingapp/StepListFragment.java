package direction123.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import direction123.bakingapp.adapters.StepAdapter;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

public class StepListFragment extends Fragment implements StepAdapter.stepAdapterOnClickHandler{
    public static final String RECIPE = "recipe";
    public static final String STEP = "step";
    private RecyclerView mRecyclerView;
    private StepAdapter mAdapter;

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

        if(savedInstanceState == null) {
            Recipe recipe = getArguments().getParcelable(RECIPE);

            TextView ingredientsTextView = (TextView) rootView.findViewById(R.id.ingredients_list);
            if (recipe != null) {
                ingredientsTextView.setText(recipe.getIngredients());
            }

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_steps);
            GridLayoutManager layoutManager = new GridLayoutManager(
                    getContext(),
                    getResources().getInteger(R.integer.grid_columns));
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new StepAdapter(this);
            mRecyclerView.setAdapter(mAdapter);
            if (recipe != null) {
                mAdapter.setstepList(recipe.getStepList());
            }
        }
        return rootView;
    }

    @Override
    public void onClick(Step step) {
        Toast.makeText(getContext(), step.getShortDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
