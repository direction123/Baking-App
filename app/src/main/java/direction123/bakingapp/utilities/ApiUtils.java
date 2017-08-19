package direction123.bakingapp.utilities;

import direction123.bakingapp.models.RetrofitClient;
import direction123.bakingapp.models.SOService;

/**
 * Created by fangxiangwang on 8/19/17.
 */

//https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
public class ApiUtils {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
