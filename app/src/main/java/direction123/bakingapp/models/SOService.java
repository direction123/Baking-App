package direction123.bakingapp.models;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
public interface SOService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}