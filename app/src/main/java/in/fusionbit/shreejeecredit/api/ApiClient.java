package in.fusionbit.shreejeecredit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.fusionbit.shreejeecredit.BuildConfig;
import in.fusionbit.shreejeecredit.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rutvikmehta on 18/12/17.
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {

            //Create OkHttp Client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            //Set Logging interceptor
            if (BuildConfig.DEBUG) {
                //Create Logging interceptor
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(loggingInterceptor);
            }

            //Create GSON  for JSON parsing
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            //Create Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }

        return retrofit;
    }

}
