package direction123.bakingapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by fangxiangwang on 8/19/17.
 */

@Table(name = "recipes")
public class RecipeDB extends Model {
    @Column(name = "recipe_id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "servings")
    public int servings;

    public RecipeDB() {
        super();
    }

    public RecipeDB(int id, String name, int servings) {
        super();
        this.id = id;
        this.name = name;
        this.servings = servings;
    }

    public List<IngredientDB> Ingredients() {
        return getMany(IngredientDB.class, "Recipe");
    }

    public List<StepDB> Steps() {
        return getMany(StepDB.class, "Recipe");
    }
}
