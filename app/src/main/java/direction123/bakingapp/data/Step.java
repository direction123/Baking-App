package direction123.bakingapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by fangxiangwang on 8/19/17.
 */

@Table(name = "steps")
public class Step extends Model {
    @Column(name = "id")
    public int id;

    @Column(name = "short_description")
    public String shortDescription;

    @Column(name = "description")
    public String description;

    @Column(name = "video_url")
    public String videoURL;

    @Column(name = "recipe", onDelete = Column.ForeignKeyAction.CASCADE)
    public Recipe recipe;

    public Step() {
        super();
    }

    public Step(int id, String shortDescription, String description, String videoURL, Recipe recipe) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.recipe = recipe;
    }
}
