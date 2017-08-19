package direction123.bakingapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by fangxiangwang on 8/19/17.
 */

@Table(name = "recipes")
public class Recipe  extends Model {
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "servings")
    public int servings;

    public Recipe() {
        super();
    }

    public Recipe(int id, String name, int servings) {
        super();
        this.id = id;
        this.name = name;
        this.servings = servings;
    }

    public List<Ingredient> Ingredients() {
        return getMany(Ingredient.class, "Recipe");
    }

    public List<Step> Steps() {
        return getMany(Step.class, "Recipe");
    }
}
