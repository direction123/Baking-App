package direction123.bakingapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by fangxiangwang on 8/19/17.
 */

@Table(name = "recipes_widget")
public class RecipeWidget extends Model {
    @Column(name = "recipe_id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "servings")
    public int servings;

    @Column(name = "ingredients")
    public String ingredients;

    public RecipeWidget() {
        super();
    }

    public RecipeWidget(int id, String name, int servings, String ingredients) {
        super();
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.ingredients = ingredients;
    }
}
