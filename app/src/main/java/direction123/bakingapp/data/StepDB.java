package direction123.bakingapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by fangxiangwang on 8/19/17.
 */

@Table(name = "steps")
public class StepDB extends Model {
    @Column(name = "step_id")
    public int id;

    @Column(name = "short_description")
    public String shortDescription;

    @Column(name = "description")
    public String description;

    @Column(name = "video_url")
    public String videoURL;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    public RecipeDB recipe;

    public StepDB() {
        super();
    }

    public StepDB(int id, String shortDescription, String description, String videoURL, RecipeDB recipe) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.recipe = recipe;
    }
}
