package direction123.bakingapp.models;

/**
 * Created by fangxiangwang on 8/19/17.
 */

//https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}