package direction123.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import direction123.bakingapp.R;
import direction123.bakingapp.models.Recipe;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeList;
    private final RecipeAdapterOnClickHandler mClickHandler;


    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNameView;
        public TextView mServingsView;

        public RecipeViewHolder(View view) {
            super(view);
            mNameView = (TextView) view.findViewById(R.id.recipe_item_name);
            mServingsView = (TextView) view.findViewById(R.id.recipe_item_servings);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @Override
    public int getItemCount() {
        if(mRecipeList != null)
            return mRecipeList.size();
        return 0;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.recipe_list_item, parent, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        TextView nameView = holder.mNameView;
        String name = recipe.getNmae();
        nameView.setText(name);

        TextView servingView = holder.mServingsView;
        int servings = recipe.getServings();
        String servingsText = "Servings: " + String.valueOf(servings);
        servingView.setText(servingsText);
    }

    public void setRecipeList(List<Recipe> recipeList){
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }
}
